<template>
    <div class = "cell-num">
        <el-row>
            <el-col :span = "18" style="padding-right:50px">
                <h4 class="title">本年月采购趋势图</h4>
                <ve-histogram ref="chart" height="400px"  :legend-visible="false" :data="data" :extend="extend" :colors="['#3aa1ff']" auto-resize> </ve-histogram>
            </el-col>
            <el-col :span = "6">
                <h4 class="title">供应商采购排行榜（本年）</h4>
                <ul class="list">
                    <li :key="index" v-for="(item, index) in cgphbList">
                        <span :class="index < 5 ? 'active' : null">{{ index + 1 }}</span>
                        <span>{{ item.name }}</span>
                        <span>{{ item.cgje }}万</span>
                    </li>
                </ul>
            </el-col>
        </el-row>
        <el-row>

        </el-row>
    </div>
</template>

<script>
  import 'echarts/lib/chart/line'
  import 'echarts/lib/chart/bar'
  import 'echarts/lib/component/tooltip'
  import IndexDataService from '@/api/sys/IndexDataService'

  export default {
    name: 'VisitNum',
    data () {
      const cgphbList = [];
      return {
        options: {
          cellNum: {}
        },
        data: {
          rows: [
            {'日期': '1月', '销售额': '0'}, {'日期': '2月', '采购额': '0'}, {'日期': '3月', '采购额': '0'}, {'日期': '4月', '采购额': '0'},
            {'日期': '5月', '采购额': '0'}, {'日期': '6月', '采购额': '0'}, {'日期': '7月', '采购额': '0'}, {'日期': '8月', '采购额': '0'},
            {'日期': '9月', '采购额': '0'}, {'日期': '10月', '采购额': '0'}, {'日期': '11月', '采购额': '0'}, {'日期': '12月', '采购额': '0'},
          ],
          columns: ['日期', '采购额'],
        },
        extend: {
          grid: {
            top: 30,
            bottom: 30,
            left: '3%',
            right: '3%',
          },
          series: {
            barWidth : 50,
            label: {
              show: true,
              position: "top",
              formatter: '{c}万'
            },
          },
          tooltip : {
            formatter: function (params) {
              return params[0].name + '<br/>' + "采购额：" + params[0].value + "万元";
            }
          },
          yAxis: {
            name: "单位（万元）",
          }
        },
        cgphbList
      }
    },
    activated () {

    },
    indexDataService: null,
    created () {
      this.indexDataService = new IndexDataService();
    },
    methods: {
      resize () {
        this.$refs.chart.resize()
      },
      getCgeData () {
        return new Promise(resolve => {
          this.indexDataService.getCgeData().then(({data}) => {
            if (data.cge != null && data.cge.length > 0) {
              this.data.rows = data.cge;
            }
            if (data.cgphb != null && data.cgphb.length > 0) {
              this.cgphbList = data.cgphb;
            } else {
              this.cgphbList = [{name: '暂无信息', xsje: '0'}];
            }
          })
        })
      },
    },
    mounted () {
      this.getCgeData()
    }
  }
</script>

<style scoped lang = "less">
    .cell-num{
        .echarts {
            width: 100%;
            height: 400px;
        }
        .list {
            margin: 25px 0 0;
            padding: 0;
            list-style: none;
            li {
                margin-top: 16px;
                span {
                    color: rgba(0, 0, 0, .65);
                    font-size: 14px;
                    line-height: 22px;

                    &:first-child {
                        background-color: #f5f5f5;
                        border-radius: 20px;
                        display: inline-block;
                        font-size: 12px;
                        font-weight: 600;
                        margin-right: 10px;
                        height: 20px;
                        line-height: 20px;
                        width: 20px;
                        text-align: center;
                    }
                    &.active {
                        background-color: #314659;
                        color: #fff;
                    }
                    &:last-child {
                        float: right;
                    }
                }
            }
        }
    }
</style>

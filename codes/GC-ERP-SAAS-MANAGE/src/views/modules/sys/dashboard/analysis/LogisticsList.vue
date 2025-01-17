<template>
  <div class="cell-num">
    <el-row>
      <el-col :span="12" style="padding-right: 100px;">
        <h4 class="title">物流运输价值分析</h4>
        <div id="pieAll" style="height: 400px;width: 100%;"></div>
      </el-col>
      <el-col :span="4" style="padding-right: 30px;margin-left:80px">
        <h4 class="title">物流公司排名TOP10</h4>
        <ul class="list">
          <li :key="index" v-for="(item, index) in chjzphList">
            <span :class="index < 5 ? 'active' : null">{{ index + 1 }}</span>
            <span>{{ item.custom_name + '（' + item.license + '）' }}</span>
          </li>
        </ul>
      </el-col>
      <el-col :span="4" style="padding-right: 30px; margin-left:80px">
        <h4 class="title">司机排名TOP10</h4>
        <ul class="list">
          <li :key="index" v-for="(item, index) in chjzphList">
            <span :class="index < 5 ? 'active' : null">{{ index + 1 }}</span>
            <span>{{ item.contacts_name }}</span>
          </li>
        </ul>
      </el-col>
      <!-- + '，' + item.contacts_name -->
    </el-row>
  </div>
</template>

<script>
import * as echarts from "echarts"
import LogisticsJs from '@/api/sys/LogisticsJs'

export default {
  name: 'Kctj',
  components: {

  },
  data() {
    const chjzphList = [];

    return {
      myChart: null,
      jzfx: ['0', '0', '0'],
      chjzphList,
      tableData: []
    }
  },
  activated() {

  },
  LogisticsJs: null,
  created() {
    this.LogisticsJs = new LogisticsJs();
  },
  methods: {
    resize() {
      this.myChart.resize()
    },
    pieBtn() {
      this.myChart = echarts.init(document.getElementById('pieAll'))
      let option = {
        tooltip: {
          confine: true,
          trigger: 'item',
          formatter: '{b}: {c}万</br>占比: {d}%'
        },
        legend: {
          bottom: '10',
          right: '10',
          icon: 'circle',
          orient: 'vertical',
          textStyle: {
            color: '#333',
            fontSize: 14
          },
          data: ['代收费用', '其他费用', '运输费用']
        },
        series: [
          {
            name: '',
            type: 'pie',
            radius: '75%',
            center: ['50%', '50%'],
            data: [
              {
                value: this.jzfx[0], name: '代收费用',
                itemStyle: {
                  normal: {
                    color: "rgba(24 , 144, 255, 0.8)",
                  }
                }
              },
              {
                value: this.jzfx[1], name: '其他费用',
                itemStyle: {
                  normal: {
                    color: 'rgba(64, 214, 251, 0.8)',
                  }
                }
              },
              {
                value: this.jzfx[2], name: '运输费用',
                itemStyle: {
                  normal: {
                    color: "rgba(215 , 125, 452, 0.8)",
                  }
                }
              },
            ].sort(function (a, b) { return a.value - b.value; }),
            roseType: 'radius',
            label: {
              position: 'inside',
              fontSize: 16,
              color: '#FFFFFF',
              formatter: '{b}\n ({d}%) '
            },
            itemStyle: {
              shadowColor: 'rgba(160, 234, 253, 1)',
              shadowBlur: 5
            },
            animationType: 'scale',
            animationEasing: 'elasticOut',
            animationDelay: function (idx) {
              return Math.random() * 200;
            }
          }
        ]
      };
      this.myChart.setOption(option)
      // 让图表跟随屏幕自动的去适应
      window.addEventListener('resize', function () {
        this.myChart.resize()
      })
    },
    getKctjData() {
      return new Promise(resolve => {
        this.LogisticsJs.getKctjData().then(({ data }) => {
          if (data.kcjzfx != null && data.kcjzfx.length > 0) {
            console.info(data.kcjzfx)
            this.jzfx = data.kcjzfx;
            this.pieBtn();
          }
          if (data.chjzph != null && data.chjzph.length > 0) {
            console.info(data.chjzph)
            this.chjzphList = data.chjzph;
          } else {
            this.cgphbList = [{ inventory_name: '暂无信息', chjz: '0' }];
          }
          if (data.chyj != null && data.chyj.length > 0) {
            this.tableData = data.chyj;
          }
        })
      })
    },
  },
  mounted() {
    this.getKctjData()
  }
}
</script>

<style scoped lang = "less">
.cell-num {
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
        white-space: nowrap;
        /*强制单行显示*/
        text-overflow: ellipsis;
        /*超出部分省略号表示*/
        overflow: hidden;
        /*超出部分隐藏*/
        width: calc(100% - 155px);
        /*设置显示的最大宽度*/
        display: inline-block;

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
          text-align: right;
          float: right;
          width: 120px;
        }
      }
    }
  }

  .yj_list {
    margin: 25px 0 0;
    padding: 0;
    list-style: none;

    li {
      margin-top: 16px;

      span {
        color: rgba(0, 0, 0, .65);
        font-size: 14px;
        line-height: 22px;
      }
    }
  }

  /* 整个滚动条 */
  ::-webkit-scrollbar {
    width: 10px;
    height: 10px;
  }

  /* 滚动条上的滚动滑块 */
  ::-webkit-scrollbar-thumb {
    background-color: #49b1f5;
    /* 关键代码 */
    // background-image: -webkit-linear-gradient(45deg,
    //         rgba(255, 255, 255, 0.4) 25%,
    //         transparent 25%,
    //         transparent 50%,
    //         rgba(255, 255, 255, 0.4) 50%,
    //         rgba(255, 255, 255, 0.4) 75%,
    //         transparent 75%,
    //         transparent);
    border-radius: 32px;
  }

  /* 滚动条轨道 */
  ::-webkit-scrollbar-track {
    background-color: #dbeffd;
    border-radius: 32px;
  }
}</style>

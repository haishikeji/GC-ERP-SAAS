<template>
  <div class="cell-num">
    <el-row>
      <el-col :span="12" style="padding-right: 100px;">
        <h4 class="title">客服服务类型分析</h4>
        <div id="pieAllOne" style="height: 400px;width: 100%;"></div>
      </el-col>
      <el-col :span="12" style="padding-right: 100px;">
        <h4 class="title">服务订单状态分析</h4>
        <div id="pieAllTwo" style="height: 400px;width: 100%;"></div>
      </el-col>

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
      myChart1: null,
      myChart2: null,
      jzfx: ['0', '0', '0'],
      chjzph: ['0', '0', '0'],
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
      this.myChart1.resize()
      this.myChart2.resize()
    },
    pieBtn() {
      this.myChart1 = echarts.init(document.getElementById('pieAllOne'))

      var pieData = [
        {
          value: this.jzfx[0], name: '咨询',
          itemStyle: {
            normal: {
              color: "rgba(84, 111, 255, 0.8)",
            }
          }
        },
        {
          value: this.jzfx[1], name: '建议',
          itemStyle: {
            normal: {
              color: 'rgba(23, 214, 251, 0.8)',
            }
          }
        },
        {
          value: this.jzfx[2], name: '投诉',
          itemStyle: {
            normal: {
              color: "rgba(15 , 125, 452, 0.8)",
            }
          }
        }
      ]
      var option = {
        legend: {
          show: true,
          icon: 'circle',
        },
        color: [
          '#5470c6',
          '#91cc75',
          '#fac858'
        ],
        series: [
          {
            type: 'pie',
            data: pieData,
            label: {
              show: true,
              // formatter: 'test' 
              formatter: function (arg) {
                return arg.name + arg.percent + '%'
              }
            },

            // radius: 20, // 饼图的半径
            // radius: '20%', 
            // radius: ['50%', '75%'], // 第0个元素代表的是內圆的半径 第1个元素外圆的半径
            radius: [60, 140],
            // roseType: 'radius', 
            // selectedMode: 'single', // 选中的效果,能够将选中的区域偏离圆点一小段距离(单个)
            selectedMode: 'multiple', // 多个可以选中
            // selectedOffset: 0
          }
        ]
      }

      this.myChart1.setOption(option)
      // 让图表跟随屏幕自动的去适应
      window.addEventListener('resize', function () {
        this.myChart1.resize()
      })
    },
    pieBtnTwo() {
      this.myChart2 = echarts.init(document.getElementById('pieAllTwo'))
      let option = {
        tooltip: {
          confine: true,
          trigger: 'item',
          formatter: '{b}: {c}个</br>占比: {d}%'
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
          data: ['处理', '归档', '分配处理']
        },
        series: [
          {
            name: '',
            type: 'pie',
            radius: '75%',
            center: ['50%', '50%', '50%'],
            data: [
              {
                value: this.chjzph[0], name: '处理',
                itemStyle: {
                  normal: {
                    color: "rgba(24 , 144, 255, 0.8)",
                  }
                }
              },
              {
                value: this.chjzph[1], name: '归档',
                itemStyle: {
                  normal: {
                    color: 'rgba(64, 214, 251, 0.8)',
                  }
                }
              },
              {
                value: this.chjzph[2], name: '分配处理',
                itemStyle: {
                  normal: {
                    color: "rgba(215 , 125, 452, 0.8)",
                  }
                }
              },
              // return arg.name + arg.percent + '%'
            ].sort(function (a, b) { return a.value; }),
            // roseType: 'radius',
            // label: {
            //   position: 'inside',
            //   fontSize: 16,
            //   color: '#FFFFFF',
            //   formatter: '{b}\n ({d}%) '
            // },
            // itemStyle: {
            //   shadowColor: 'rgba(160, 234, 253, 1)',
            //   shadowBlur: 5
            // },
            // animationType: 'scale',
            // animationEasing: 'elasticOut',
            // animationDelay: function (idx) {
            //   return Math.random() * 200;
            // }
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      this.myChart2.setOption(option)
      // 让图表跟随屏幕自动的去适应
      window.addEventListener('resize', function () {
        this.myChart2.resize()
      })
    },
    getKctjData() {
      return new Promise(resolve => {
        this.LogisticsJs.getCusData().then(({ data }) => {
          if (data.kcjzfx != null && data.kcjzfx.length > 0) {
            this.jzfx = data.kcjzfx;
            this.pieBtn();
          }
          if (data.chjzph != null && data.chjzph.length > 0) {
            this.chjzph = data.chjzph;
            this.pieBtnTwo();
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
}
</style>

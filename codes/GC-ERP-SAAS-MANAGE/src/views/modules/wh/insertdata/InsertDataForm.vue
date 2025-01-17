<template>
<div>
    <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'"
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="12">
            <el-form-item label="开始时间" prop="stratTime"
                :rules="[
                 ]">
                <el-date-picker
                      style="width: 100%;"
                      v-model="inputForm.stratTime"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      placeholder="选择日期时间">
                    </el-date-picker>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="结束时间" prop="endTime"
                :rules="[
                 ]">
                <el-date-picker
                      style="width: 100%;"
                      v-model="inputForm.endTime"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      placeholder="选择日期时间">
                    </el-date-picker>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="截止时间" prop="jzendTime"
                :rules="[
                 ]">
                <el-date-picker
                      style="width: 100%;"
                      v-model="inputForm.jzendTime"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      placeholder="选择日期时间">
                    </el-date-picker>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="销售月最大总额   单位(万元)" prop="productMax"
                :rules="[]">
              <el-input v-model="inputForm.productMax" placeholder="请填写销售月最大总额"> </el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="月销售单数" prop="producMonthCount"
                :rules="[

                 ]">
              <el-input v-model="inputForm.producMonthCount" placeholder="请填写月销售单数"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="编码规则" prop="bianma"
                :rules="[

                 ]">
              <el-input v-model="inputForm.bianma" placeholder="请填写编码规则前缀"     ></el-input>
           </el-form-item>
        </el-col>
        <!-- <el-col :span="12">
            <el-form-item label="库存总价值过大" prop="kcjzd"
                :rules="[
                 ]">
              <el-input v-model="inputForm.kcjzd" placeholder="请填写数值"     ></el-input>
           </el-form-item>
        </el-col> -->
        <el-col :span="12">
            <el-form-item label="库存总价值过小" prop="kcjzdmin"
                :rules="[
                 ]">
              <el-input v-model="inputForm.kcjzdmin" placeholder="过大乘以0.*,过小乘以*"     ></el-input>
           </el-form-item>
        </el-col>
       <!-- <el-col :span="12">
            <el-form-item label="库存总价值" prop="kczj"
                :rules="[
                 ]">
              <el-input v-model="inputForm.kczj" placeholder="请填写数值"     ></el-input>
           </el-form-item>
        </el-col> -->
        <!-- <el-col :span="12">
            <el-form-item label="月销售详情次数" prop="producMonthCount"
                :rules="[

                 ]">
              <el-input v-model="inputForm.producMonthDelCount" placeholder="月销售详情次数"     ></el-input>
           </el-form-item>
        </el-col> -->
        </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <!-- <el-button size="small" @click="visible = false">关闭</el-button> -->
      <el-button size="small" type="primary" v-if="method != 'view'" @click="suijixm()" v-noMoreClick>随机生成姓名</el-button>
      <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" v-noMoreClick>生成销售</el-button>
      <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmits()" v-noMoreClick>采购</el-button>
      <!-- <el-button size="small" type="primary" v-if="method != 'view'" @click="xiugaikc()" v-noMoreClick>修改库存价值</el-button> -->
    <!-- <el-button size="small" type="primary" v-if="method != 'view'" @click="xiugaimax()" v-noMoreClick>修改最大值</el-button> -->
       <el-button size="small" type="primary" v-if="method != 'view'" @click="xiugaimin()" v-noMoreClick>修改库存</el-button>
       <!-- <el-button size="small" type="primary" v-if="method != 'view'" @click="salaze()" v-noMoreClick>修改销售总额为00</el-button>
       <el-button size="small" type="primary" v-if="method != 'view'" @click="purze()" v-noMoreClick>修改采购总额为00</el-button> -->
       <!-- <el-button size="small" type="primary" v-if="method != 'view'" @click="salaslze()" v-noMoreClick>修改销售数量为00</el-button>
       <el-button size="small" type="primary" v-if="method != 'view'" @click="purslze()" v-noMoreClick>修改采购数量为00</el-button> -->
     <!-- <el-button size="small" @click="updateIn()">更新库存数量</el-button> -->
    </span>
</div>
</template>

<script>
  import InsertDataService from '@/api/wh/insertdata/InsertDataService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          stratTime: '',
          endTime: '',
          jzendTime: '',
          productYearMax: '',
          productYearMin: '',
          purYearMax: '',
          purYearMin: '',
          productMax: '',
          productMin: '',
          purMax: '',
          purMin: '',
          kcjzd:'',
          kcjzdmin:'',
          kczj:'',
          producMonthCount: '',
          purMonthCount: '',
          producMonthDelCount: '',
          purMonthDelCount: ''
        }
      }
    },
    components: {
    },
    insertDataService: null,
    created () {
      this.insertDataService = new InsertDataService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建插入数据`
        } else if (method === 'edit') {
          this.title = '修改插入数据'
        } else if (method === 'view') {
          this.title = '查看插入数据'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.insertDataService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      // updateIn(){
      //   this.insertDataService.updateIn();
      // },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.insertDataService.save(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      xiugaikc () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.insertDataService.xiugaikc(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      suijixm () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.insertDataService.suijixm(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      doSubmits () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.insertDataService.saves(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      xiugaimax () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.insertDataService.xiugaimax(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      xiugaimin () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.insertDataService.xiugaimin(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      salaze () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.insertDataService.salaze(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      purze () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.insertDataService.purze(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      salaslze () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.insertDataService.salaslze(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
      // 表单提交xiugaikc
      purslze () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.insertDataService.purslze(this.inputForm).then(({data}) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      }
    }
  }
</script>

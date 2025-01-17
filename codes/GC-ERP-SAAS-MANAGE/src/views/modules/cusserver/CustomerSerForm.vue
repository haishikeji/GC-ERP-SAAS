<template>
<div>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'"
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="12">
            <el-form-item label="客户名称" prop="customerId"
                :rules="[
                 ]">
              <el-input v-model="inputForm.customerId" placeholder="请填写客户名称"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="咨询个数" prop="serverType1"
                :rules="[
                 ]">
              <el-input v-model="inputForm.serverType1" placeholder="请填写咨询个数"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="建议个数" prop="serverType2"
                :rules="[
                 ]">
              <el-input v-model="inputForm.serverType2" placeholder="请填写建议个数"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="投诉个数" prop="serverType3"
                :rules="[
                 ]">
              <el-input v-model="inputForm.serverType3" placeholder="请填写投诉个数"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="处理个数" prop="serverState1"
                :rules="[
                 ]">
              <el-input v-model="inputForm.serverState1" placeholder="请填写处理个数"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="归档个数" prop="serverState2"
                :rules="[
                 ]">
              <el-input v-model="inputForm.serverState2" placeholder="请填写归档个数"     ></el-input>
           </el-form-item>
        </el-col>
    <el-col :span="24">
     <el-form-item label-width="0">
            <el-tabs v-model="customerSerTab">
            </el-tabs>
           </el-form-item>
          </el-col>
        </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="visible = false">关闭</el-button>
      <el-button size="small" type="primary" v-if="method != 'view'" @click="doSubmit()" v-noMoreClick>确定</el-button>
    </span>
  </el-dialog>
</div>
</template>

<script>
  import CustomerSerService from '@/api/cusserver/CustomerSerService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        customerSerTab: '0',
        inputForm: {
          id: '',
          customerId: '',
          serverType1: '',
          serverType2: '',
          serverType3: '',
          serverState1: '',
          serverState2: ''
        }
      }
    },
    components: {
    },
    customerSerService: null,
    created () {
      this.customerSerService = new CustomerSerService()
    },
    methods: {
      init (method, id) {
        this.method = method
        if (method === 'add') {
          this.title = `新建服务类型分析`
        } else if (method === 'edit') {
          this.title = '修改服务类型分析'
        } else if (method === 'view') {
          this.title = '查看服务类型分析'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.id = id
          this.customerSerTab = '0'
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.customerSerService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.customerSerService.save(this.inputForm).then(({data}) => {
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

  

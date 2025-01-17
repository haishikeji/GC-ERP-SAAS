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
            <el-form-item label=" 供应商名称" prop="supplierName"
                :rules="[
                  {required: true, message:' 供应商名称不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.supplierName" placeholder="请填写 供应商名称"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="联系人" prop="contacts"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contacts" placeholder="请填写联系人"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="联系电话" prop="contactNum"
                :rules="[
                  {required: true, message:'联系人电话不能为空', trigger:'blur'},
                  {validator:validator.isMobile, trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.contactNum" placeholder="请填写联系电话"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="联系地址" prop="contactAddr"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactAddr" placeholder="请填写联系地址"     ></el-input>
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
  import SupplierManagementService from '@/api/basic/suppliermanagement/SupplierManagementService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          supplierName: '',
          contacts: '',
          contactNum: '',
          contactAddr: ''
        }
      }
    },
    components: {
    },
    supplierManagementService: null,
    created () {
      this.supplierManagementService = new SupplierManagementService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建供应商管理`
        } else if (method === 'edit') {
          this.title = '修改供应商管理'
        } else if (method === 'view') {
          this.title = '查看供应商管理'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.supplierManagementService.queryById(this.inputForm.id).then(({data}) => {
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
            this.supplierManagementService.save(this.inputForm).then(({data}) => {
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

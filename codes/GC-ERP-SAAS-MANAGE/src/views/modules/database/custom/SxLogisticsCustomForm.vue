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
            <el-form-item label="物流公司名称" prop="customName"
                :rules="[
                 ]">
              <el-input v-model="inputForm.customName" placeholder="请填写物流公司名称"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="电话" prop="customCall"
                :rules="[
                 ]">
              <el-input v-model="inputForm.customCall" placeholder="请填写电话"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="邮编" prop="zip"
                :rules="[
                 ]">
              <el-input v-model="inputForm.zip" placeholder="请填写邮编"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="地址" prop="address"
                :rules="[
                 ]">
              <el-input v-model="inputForm.address" placeholder="请填写地址"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="联系人" prop="contactsName"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactsName" placeholder="请填写联系人"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="联系人手机" prop="contactsPhone"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactsPhone" placeholder="请填写联系人手机"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="车牌" prop="license"
                :rules="[
                 ]">
              <el-input v-model="inputForm.license" placeholder="请填写车牌"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="车型" prop="contactsVehicle"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactsVehicle" placeholder="请填写车型"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="车厢长度" prop="contactsLong"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactsLong" placeholder="请填写车厢长度"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="车厢宽度" prop="contactsWide"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactsWide" placeholder="请填写车厢宽度"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="载物高度" prop="contactsHeight"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactsHeight" placeholder="请填写载物高度"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="驾驶证" prop="contactsDriver"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactsDriver" placeholder="请填写驾驶证"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="行驶证" prop="contactsDriving"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactsDriving" placeholder="请填写行驶证"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="营运证" prop="contactsTrading"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactsTrading" placeholder="请填写营运证"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="保险证" prop="contactsCertificate"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactsCertificate" placeholder="请填写保险证"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="车架号" prop="contactsVin"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactsVin" placeholder="请填写车架号"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="发动机号" prop="contactsEngine"
                :rules="[
                 ]">
              <el-input v-model="inputForm.contactsEngine" placeholder="请填写发动机号"     ></el-input>
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
  import SxLogisticsCustomService from '@/api/custom/SxLogisticsCustomService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          customName: '',
          customCall: '',
          zip: '',
          address: '',
          contactsName: '',
          contactsPhone: '',
          license: '',
          contactsVehicle: '',
          contactsLong: '',
          contactsWide: '',
          contactsHeight: '',
          contactsDriver: '',
          contactsDriving: '',
          contactsTrading: '',
          contactsCertificate: '',
          contactsVin: '',
          contactsEngine: ''
        }
      }
    },
    components: {
    },
    sxLogisticsCustomService: null,
    created () {
      this.sxLogisticsCustomService = new SxLogisticsCustomService()
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建物流信息`
        } else if (method === 'edit') {
          this.title = '修改物流信息'
        } else if (method === 'view') {
          this.title = '查看物流信息'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.sxLogisticsCustomService.queryById(this.inputForm.id).then(({data}) => {
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
            this.sxLogisticsCustomService.save(this.inputForm).then(({data}) => {
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

  

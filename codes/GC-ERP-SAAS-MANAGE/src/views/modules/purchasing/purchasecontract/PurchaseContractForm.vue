<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method==='view'?'readonly':''" :disabled="method==='view'" label-width="120px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="采购合同编号" prop="purchaseCode">
              <el-input v-model="inputForm.purchaseCode" placeholder="自动生成" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供应商" prop="supplier.id" :rules="[
                  {required: true, message:'供应商不能为空', trigger:'blur'}
                 ]">
              <GridSelect title="选择供应商" labelName='supplierName' labelValue='id' :value="inputForm.supplier.id"
                :limit="1" @getValue='(value) => {inputForm.supplier.id=value}' :columns="[
              {
                prop: 'supplierName',
                label: '供应商名称'
              },
              {
                prop: 'concat',
                label: '联系人'
              },
              {
                prop: 'phone',
                label: '电话'
              },
              {
                prop: 'address',
                label: '地址'
              },
              {
                prop: 'supplierLevel',
                label: '供应商等级'
              }
              ]" :searchs="[
              {
                prop: 'supplierName',
                label: '供应商名称'
              }
              ]" dataListUrl="/supplier/inventoryset/supplier/list"
                queryEntityUrl="/supplier/inventoryset/supplier/queryById">
              </GridSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同时间" prop="purchaseData" :rules="[
                  {required: true, message:'合同时间不能为空', trigger:'blur'}
                 ]">
              <el-date-picker style="width: 100%;" v-model="inputForm.purchaseData" type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同备注" prop="purchaseResult" :rules="[
                 ]">
              <el-input v-model="inputForm.purchaseResult" placeholder="请填写合同备注"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="附件" prop="purchaseFiles">
              <el-upload ref="files"
                :action="`${this.$http.BASE_URL}/sys/file/webupload/upload?uploadPath=/purchasing/purchasecontract`"
                :headers="{token: $cookie.get('token')}"
                :on-preview="(file, fileList) => {$window.location.href = file.url}" :on-success="(response, file, fileList) => {
                      inputForm.purchaseFiles = fileList.map(item => item.url || item.response.url).join('|')
                   }" :on-error="(response) => {
                     $message.error(response.message)
                   }" :on-remove="(file, fileList) => {
                     $http.delete(`/sys/file/webupload/deleteByUrl?url=${file.url}`).then(({data}) => {
                       $message.success(data)
                     })
                     inputForm.purchaseFiles = fileList.map(item => item.url).join('|')
                   }" :before-remove="(file, fileList) => {
                     return $confirm(`确定移除 ${file.name}？`)
                   }" multiple :limit="5" :on-exceed="(files, fileList) =>{
                     $message.warning(`当前限制选择 5 个文件，本次选择了 ${purchaseFiles.length} 个文件，共选择了 ${purchaseFiles.length + fileList.length} 个文件`)
                   }" :file-list="filesArra">
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">添加相关附件</div>
              </el-upload>
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
  import GridSelect from '@/components/gridSelect'
  import PurchaseContractService from '@/api/purchasing/purchasecontract/PurchaseContractService'
  export default {
    data() {
      return {
        title: '',
        method: '',
        filesArra: [],
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          purchaseCode: '',
          supplier: {
            id: ''
          },
          purchaseData: '',
          purchaseFiles: '',
          purchaseResult: ''
        }
      }
    },
    components: {
      GridSelect
    },
    purchaseContractService: null,
    created() {
      this.purchaseContractService = new PurchaseContractService()
    },
    methods: {
      init(method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建采购合同`
        } else if (method === 'edit') {
          this.title = '修改采购合同'
        } else if (method === 'view') {
          this.title = '查看采购合同'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.$refs.files.clearFiles()
          this.filesArra = []
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.purchaseContractService.queryById(this.inputForm.id).then(({
              data
            }) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.inputForm.purchaseFiles.split('|').forEach((item) => {
                if (item.trim().length > 0) {
                  this.filesArra.push({
                    name: decodeURIComponent(item.substring(item.lastIndexOf('/') + 1)),
                    url: item
                  })
                }
              })
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      doSubmit() {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.purchaseContractService.save(this.inputForm).then(({
              data
            }) => {
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

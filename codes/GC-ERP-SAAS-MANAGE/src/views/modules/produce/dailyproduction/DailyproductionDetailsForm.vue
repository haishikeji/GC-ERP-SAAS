<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible">
    <el-form size="small" :model="inputForm" ref="inputForm" :class="method==='view'?'readonly':''"  :disabled="method==='view'" @keyup.enter.native="doSubmit()"
             label-width="120px">
      <el-row  :gutter="15">

         <el-col :span="12">
            <el-form-item label="产品名称" prop="inventoryName"
                :rules="[
                 ]">
            <!--  <el-input v-model="inputForm.inventory.inventoryName" placeholder="请填写产品ID"     ></el-input> -->
              <el-input v-model="inputForm.inventoryName" placeholder="请填写产品名称"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="完成数量" prop="completedQuantity"
                :rules="[
                 ]">
              <el-input v-model="inputForm.completedQuantity" placeholder="请填写完成数量"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="良品数量" prop="goodProducts"
                :rules="[
                 ]">
              <el-input v-model="inputForm.goodProducts" placeholder="请填写良品数量"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="不良数量" prop="noProducts"
                :rules="[
                 ]">
              <el-input v-model="inputForm.noProducts" placeholder="请填写不良数量"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="良品率" prop="yield"
                :rules="[
                 ]">
              <el-input v-model="inputForm.yield" placeholder="请填写良品率"    :disabled="true" ></el-input>
           </el-form-item>
        </el-col>
        </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" v-if="method === 'add'" type="primary" @click="continueDoSubmit()">继续添加</el-button>
      <el-button size="small" @click="visible = false">关闭</el-button>
      <el-button size="small" v-if="method !== 'view'" type="primary" @click="doSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        oldInputForm: '',
        inputForm: {
          id: '',
          daily: {
            id: ''
          },
          inventoryId: '',
          inventoryName: '',
          completedQuantity: '',
          goodProducts: '',
          noProducts: '',
          yield: ''
        }
      }
    },
    components: {
    },
    methods: {
      init (method, obj) {
        this.method = method
        if (method === 'add') {
          this.title = `新建生产日报详情`
        } else if (method === 'edit') {
          this.title = '修改生产日报详情'
        } else if (method === 'view') {
          this.title = '查看生产日报详情'
        }
        this.visible = true
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.id = ''
          this.oldInputForm = ''
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.oldInputForm = obj
            this.inputForm = JSON.parse(JSON.stringify(obj))
          }
        })
      },
   // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.$emit('addRow', this.oldInputForm, JSON.parse(JSON.stringify(this.inputForm)))
            this.visible = false
          }
        })
      },
      continueDoSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.$emit('addRow', this.oldInputForm, JSON.parse(JSON.stringify(this.inputForm)))
            this.$refs['inputForm'].resetFields()
          }
        })
      }
    }
  }
</script>

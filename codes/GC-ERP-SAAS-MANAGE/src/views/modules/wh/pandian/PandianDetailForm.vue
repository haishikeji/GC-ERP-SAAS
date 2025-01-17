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
            <el-form-item label="存货" prop="inventory.id"
                :rules="[
                  {required: true, message:'存货id不能为空', trigger:'blur'}
                 ]">
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="在库库存" prop="inventoryInStock"
                :rules="[
                  {required: true, message:'在库库存不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.inventoryInStock" placeholder="请填写在库库存"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="盘点库存" prop="pandianStock"
                :rules="[
                  {required: true, message:'盘点库存不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.pandianStock" placeholder="请填写盘点库存"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="备注、差异原因" prop="remark"
                :rules="[
                 ]">
              <el-input v-model="inputForm.remark" placeholder="请填写备注、差异原因"     ></el-input>
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
  import InventorySelect from '@/components/inventorySelect'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        oldInputForm: '',
        inputForm: {
          id: '',
          pandian: {
            id: ''
          },
          inventory: {
            id: ''
          },
          inventoryInStock: '',
          pandianStock: '',
          remark: ''
        }
      }
    },
    components: {
      InventorySelect
    },
    methods: {
      init (method, obj) {
        this.method = method
        if (method === 'add') {
          this.title = `新建盘点单详情`
        } else if (method === 'edit') {
          this.title = '修改盘点单详情'
        } else if (method === 'view') {
          this.title = '查看盘点单详情'
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

  

<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method==='view'?'readonly':''" :disabled="method==='view'" label-width="120px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="类别名称" prop="name" :rules="[
                  {required: true, message:'类别名称不能为空', trigger:'blur'},
                  { min: 1, message: '最小长度是1个字符', trigger: 'blur' }
                 ]">
              <el-input v-model="inputForm.name" placeholder="请填写类别名称" maxlength="100" minlength="1"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类别编码" prop="typeCode" :rules="[
                  {required: true, message:'类别编码不能为空', trigger:'blur'},
                  { min: 1, message: '最小长度是1个字符', trigger: 'blur' }
                 ]">
              <el-input v-model="inputForm.typeCode" placeholder="请填写类别编码" maxlength="100" minlength="1"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级目录" prop="parent.id" :rules="[
                 ]">
              <SelectTree ref="parent" :props="{
                          value: 'id',             // ID字段名
                          label: 'name',         // 显示名称
                          children: 'children'    // 子级字段名
                        }" v-if="visible" :url="`/basic/inventorytype/inventoryType/treeData?extId=${inputForm.id}`"
                :value="inputForm.parent.id" :clearable="true" :accordion="true"
                @getValue="(value) => {inputForm.parent.id=value}" />
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
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import InventoryTypeService from '@/api/basic/inventorytype/InventoryTypeService'
  export default {
    data() {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          name: '',
          typeCode: '',
          parent: {
            id: ''
          }
        }
      }
    },
    components: {
      SelectTree
    },
    inventoryTypeService: null,
    created() {
      this.inventoryTypeService = new InventoryTypeService()
    },
    methods: {
      init(method, obj) {
        this.method = method
        this.inputForm.id = obj.id
        if (method === 'add') {
          this.title = '新建存货类别'
        } else if (method === 'addChild') {
          this.title = '添加下级存货类别'
        } else if (method === 'edit') {
          this.title = '修改存货类别'
        } else if (method === 'view') {
          this.title = '查看存货类别'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.parent.id = obj.parent.id
          this.inputForm.parent.name = obj.parent.name
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.inventoryTypeService.queryById(this.inputForm.id).then(({
              data
            }) => {
              this.inputForm = this.recover(this.inputForm, data)
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
            this.inventoryTypeService.save(this.inputForm).then(({
              data
            }) => {
              this.loading = false
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshTree')
            })
          }
        })
      }
    }
  }
</script>

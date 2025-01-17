<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method==='view'?'readonly':''" :disabled="method==='view'" label-width="120px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="供应商名称" prop="supplierName" :rules="[
                  {required: true, message:'供应商名称不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.supplierName" placeholder="请填写供应商名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="concat" :rules="[
                 ]">
              <el-input v-model="inputForm.concat" placeholder="请填写联系人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="phone" :rules="[
                 ]">
              <el-input v-model="inputForm.phone" placeholder="请填写电话"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地址" prop="address" :rules="[
                 ]">
              <el-input v-model="inputForm.address" placeholder="请填写地址"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label-width="0">
              <el-tabs v-model="supplierTab">
                <el-tab-pane label="供应商存货管理">
                  <el-button size="small" @click="addSupplierDetailRow" type="primary">新增</el-button>
                  <el-table class="table" size="small"
                    :data="inputForm.supplierDetailDTOList.filter(function(item){ return item.delFlag !== '1'})"
                    style="width: 100%">
                    <el-table-column prop="inventory.inventoryName" header-align="center" align="center"
                      show-overflow-tooltip label="存货名称">
                    </el-table-column>
                    <el-table-column prop="inventory.inventoryCode" header-align="center" align="center"
                      show-overflow-tooltip label="存货编码">
                    </el-table-column>
                    <el-table-column prop="inventory.model" header-align="center" align="center" show-overflow-tooltip
                      label="型号">
                    </el-table-column>
                    <el-table-column prop="inventory.singalUnit" header-align="center" align="center"
                      show-overflow-tooltip label="计量单位">
                      <template slot-scope="scope">
                        {{ $dictUtils.getDictLabel("sx_metering_unit", scope.row.inventory.singalUnit, '-') }}
                      </template>
                    </el-table-column>
                    <el-table-column prop="inventory.nventoryAttributeValue" header-align="center" align="center"
                      show-overflow-tooltip label="存货属性值">
                    </el-table-column>
                    <el-table-column prop="inventory.taxincludedPrice" header-align="center" align="center"
                      show-overflow-tooltip label="含税单价">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.inventory.taxincludedPrice" type="number"></el-input>
                      </template>
                    </el-table-column>

                    <el-table-column prop="remark" header-align="center" align="center" show-overflow-tooltip
                      label="备注">
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="150">
                      <template slot-scope="scope">
                        <!-- <el-button @click="viewSupplierDetailRow(scope.row)" type="text" size="small">查看</el-button>
                        <el-button @click="editSupplierDetailRow(scope.row)" type="text" size="small">编辑</el-button> -->
                        <el-button @click="delSupplierDetailRow(scope.row)" type="text" size="small">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
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
    <SupplierDetailForm ref="supplierDetailForm" @addRow="saveSupplierDetailRow(arguments)"></SupplierDetailForm>
  </div>
</template>

<script>
  import SupplierDetailForm from './SupplierDetailForm'
  import SupplierService from '@/api/supplier/inventoryset/SupplierService'
  export default {
    data() {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        supplierTab: '0',
        inputForm: {
          id: '',
          supplierDetailDTOList: [],
          supplierName: '',
          concat: '',
          phone: '',
          address: ''
        }
      }
    },
    components: {
      SupplierDetailForm
    },
    supplierService: null,
    created() {
      this.supplierService = new SupplierService()
    },
    methods: {
      init(method, id) {
        this.method = method
        if (method === 'add') {
          this.title = `新建供应商存货设置`
        } else if (method === 'edit') {
          this.title = '修改供应商存货设置'
        } else if (method === 'view') {
          this.title = '查看供应商存货设置'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.id = id
          this.supplierTab = '0'
          this.inputForm.supplierDetailDTOList = []
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.supplierService.queryById(this.inputForm.id).then(({
              data
            }) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      saveSupplierDetailRow(child) {
        if (child[0] === '') {
          this.inputForm.supplierDetailDTOList.push(child[1])
        } else {
          this.inputForm.supplierDetailDTOList.forEach((item, index) => {
            if (item === child[0]) {
              this.inputForm.supplierDetailDTOList.splice(index, 1, child[1])
            }
          })
        }
      },
      addSupplierDetailRow(child) {
        this.$refs.supplierDetailForm.init('add')
      },
      viewSupplierDetailRow(child) {
        this.$refs.supplierDetailForm.init('view', child)
      },
      editSupplierDetailRow(child) {
        this.$refs.supplierDetailForm.init('edit', child)
      },
      delSupplierDetailRow(child) {
        this.inputForm.supplierDetailDTOList.forEach((item, index) => {
          if (item === child && item.id === '') {
            this.inputForm.supplierDetailDTOList.splice(index, 1)
          } else if (item === child) {
            item.delFlag = '1'
            this.inputForm.supplierDetailDTOList.splice(index, 1, item)
          }
        })
      },
      // 表单提交
      doSubmit() {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.supplierService.save(this.inputForm).then(({
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

<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method==='view'?'readonly':''" :disabled="method==='view'" label-width="120px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="物料入库编号" prop="materialCode" :rules="[
                 ]">
              <el-input v-model="inputForm.materialCode" placeholder="自动生成" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="采购订单" prop="materialOrder.id" :rules="[]">
              <GridSelect title="选择采购订单" labelName='purCode' labelValue='id' :value="inputForm.materialOrder.id"
                @valueChange='materialChange' :limit="1" @getValue='(value) => {inputForm.materialOrder.id=value}'
                :columns="[
            {
              prop: 'purCode',
              label: '采购订单编号'
            },
            {
              prop: 'purContract.purchaseCode',
              label: '采购合同'
            },
            {
              prop: 'purRequisitions',
              label: '请购单'
            },
            {
              prop: 'totalpriceIncludingtax',
              label: '合计含税价格'
            },
            {
              prop: 'totalNumber',
              label: '总数量'
            },
            {
              prop: 'purSupplierName',
              label: '供应商'
            },
            {
              prop: 'createBy.name',
              label: '采购人'
            },
            {
              prop: 'createDate',
              label: '采购时间'
            }
            ]" :searchs="[
            {
              prop: 'purCode',
              label: '采购订单编号'
            }
            ]" dataListUrl="/purchasing/order/purchaseOrder/list1"
                queryEntityUrl="/purchasing/order/purchaseOrder/queryById">
              </GridSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料入库类型" prop="materialType" :rules="[
                 ]">
              <el-select v-model="inputForm.materialType" placeholder="自动生成" style="width: 100%;" :disabled="true">
                <el-option v-for="item in $dictUtils.getDictList('sx_storage_type')" :key="item.value"
                  :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label-width="0">
              <el-tabs v-model="materialIntoTab">
                <el-tab-pane label="物料入库详情表">
                  <el-button size="small" @click="addMaterialIntoDetailRow" type="primary">新增</el-button>
                  <el-table class="table" size="small"
                    :data="inputForm.materialIntoDetailDTOList.filter(function(item){ return item.delFlag !== '1'})"
                    style="width: 100%">
                    <el-table-column prop="inventory.inventoryName" header-align="center" align="center"
                      show-overflow-tooltip label="存货名称">
                    </el-table-column>
                    <el-table-column prop="inventory.inventoryCode" header-align="center" align="center"
                      show-overflow-tooltip label="存货编码">
                    </el-table-column>
                    <el-table-column prop="inventory.model" header-align="center" align="center" show-overflow-tooltip
                      label="规格型号">
                    </el-table-column>
                    <el-table-column prop="inventory.nventoryAttributeValue" header-align="center" align="center"
                      show-overflow-tooltip label="存货属性值">
                    </el-table-column>
                    <el-table-column prop="inventory.singalUnit" header-align="center" align="center"
                      show-overflow-tooltip label="计量单位">
                      <template slot-scope="scope">
                        {{ $dictUtils.getDictLabel("sx_metering_unit", scope.row.inventory.singalUnit, '-') }}
                      </template>
                    </el-table-column>
                    <el-table-column prop="materialIntoNum" header-align="center" align="center" show-overflow-tooltip
                      label="入库数量">
                    </el-table-column>
                    <el-table-column prop="materialIntoHouse.name" header-align="center" align="center"
                      show-overflow-tooltip label="入库仓库">
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="150">
                      <template slot-scope="scope">
                        <el-button @click="viewMaterialIntoDetailRow(scope.row)" type="text" size="small">查看</el-button>
                        <el-button @click="editMaterialIntoDetailRow(scope.row)" type="text" size="small">编辑</el-button>
                        <el-button @click="delMaterialIntoDetailRow(scope.row)" type="text" size="small">删除</el-button>
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
    <MaterialIntoDetailForm ref="materialIntoDetailForm" @addRow="saveMaterialIntoDetailRow(arguments)">
    </MaterialIntoDetailForm>
  </div>
</template>

<script>
  import MaterialIntoDetailForm from './MaterialIntoDetailForm'
  import GridSelect from '@/components/gridSelect'
  import MaterialIntoService from '@/api/wh/materialinto/MaterialIntoService'
  import PurchaseOrderService from '@/api/purchasing/order/PurchaseOrderService'
  export default {
    data() {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        materialIntoTab: '0',
        inputForm: {
          id: '',
          materialIntoDetailDTOList: [],
          materialCode: '',
          materialOrder: {
            id: ''
          },
          materialState: '',
          materialType: ''
        }
      }
    },
    components: {
      GridSelect,
      MaterialIntoDetailForm
    },
    materialIntoService: null,
    purchaseOrderService: null,
    created() {
      this.materialIntoService = new MaterialIntoService()
      this.purchaseOrderService = new PurchaseOrderService()
    },
    methods: {
      init(method, id) {
        this.method = method
        if (method === 'add') {
          this.title = `新建物料入库`
        } else if (method === 'edit') {
          this.title = '修改物料入库'
        } else if (method === 'view') {
          this.title = '查看物料入库'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.id = id
          this.materialIntoTab = '0'
          this.inputForm.materialIntoDetailDTOList = []
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.materialIntoService.queryById(this.inputForm.id).then(({
              data
            }) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      saveMaterialIntoDetailRow(child) {
        if (child[0] === '') {
          this.inputForm.materialIntoDetailDTOList.push(child[1])
        } else {
          this.inputForm.materialIntoDetailDTOList.forEach((item, index) => {
            if (item === child[0]) {
              this.inputForm.materialIntoDetailDTOList.splice(index, 1, child[1])
            }
          })
        }
      },
      addMaterialIntoDetailRow(child) {
        this.$refs.materialIntoDetailForm.init('add')
      },
      viewMaterialIntoDetailRow(child) {
        this.$refs.materialIntoDetailForm.init('view', child)
      },
      editMaterialIntoDetailRow(child) {
        this.$refs.materialIntoDetailForm.init('edit', child)
      },
      delMaterialIntoDetailRow(child) {
        this.inputForm.materialIntoDetailDTOList.forEach((item, index) => {
          if (item === child && item.id === '') {
            this.inputForm.materialIntoDetailDTOList.splice(index, 1)
          } else if (item === child) {
            item.delFlag = '1'
            this.inputForm.materialIntoDetailDTOList.splice(index, 1, item)
          }
        })
      },
      // 表单提交
      doSubmit() {
        this.inputForm.materialType = "1"
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.materialIntoService.save(this.inputForm).then(({
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
      },
      materialChange(data) {

        if (data != null && data != '' && data != undefined) {
          this.purchaseOrderService.queryById(data.id).then(({
            data
          }) => {
            if (data.purState == 4) {
              let materialIntoDetailDTOList = []
              data.purchaseOrderDetailDTOList.forEach(item => {
                if (Number(item.purOrderNum) > Number(item.purUsedNum)) {
                  this.$set(item, 'materialIntoNum', item.purUnusedNum)
                  item.materialIntoHouse = Object.assign({}, {
                    'name': item.inventory.preStorageWarehouse.name,
                    'id': item.inventory.preStorageWarehouse.id
                  })
                  item.id = null
                  materialIntoDetailDTOList.push(item)
                }
              })
              this.inputForm.materialIntoDetailDTOList = materialIntoDetailDTOList
            } else {
              let materialIntoDetailDTOList = data.purchaseOrderDetailDTOList
              materialIntoDetailDTOList.forEach(item => {
                this.$set(item, 'materialIntoNum', item.purOrderNum)
                item.materialIntoHouse = Object.assign({}, {
                  'name': item.inventory.preStorageWarehouse.name,
                  'id': item.inventory.preStorageWarehouse.id
                })
                item.id = null
              })
              this.inputForm.materialIntoDetailDTOList = materialIntoDetailDTOList
            }
          })
        }
      }
    }
  }
</script>

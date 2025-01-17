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
            <el-form-item label="应付订单编号" prop="purCode"
                :rules="[
                 ]">
              <el-input v-model="inputForm.purCode" placeholder="请填写应付订单编号"   :disabled="true"  ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="采购合同" prop="purContract.id"
                :rules="[
                 ]">
          <GridSelect
            title="选择采购合同"
            labelName = 'purchaseCode'
            labelValue = 'id'
            :disabled="true"
            :value = "inputForm.purContract.id"
            :limit="1"
            @getValue='(value) => {inputForm.purContract.id=value}'
            :columns="[
            {
              prop: 'id',
              label: 'id'
            },
            {
              prop: 'purchaseCode',
              label: '合同编号'
            },
            {
              prop: 'supplier',
              label: '供应商'
            },
            {
              prop: 'purchaseData',
              label: '合同日期'
            }
            ]"
            :searchs="[
            {
              prop: 'id',
              label: 'id'
            },
            {
              prop: 'purchaseCode',
              label: '合同编号'
            },
            {
              prop: 'supplier',
              label: '供应商'
            },
            {
              prop: 'purchaseData',
              label: '合同日期'
            }
            ]"
            dataListUrl="/purchasing/purchasecontract/purchaseContract/list"
            queryEntityUrl="/purchasing/purchasecontract/purchaseContract/queryById">
          </GridSelect>
           </el-form-item>
        </el-col>
        <!-- <el-col :span="12">
            <el-form-item label="请购单" prop="purRequisition.id"
                :rules="[
                 ]">
          <GridSelect
            title="选择请购单"
            labelName = 'requisitionNumber'
            labelValue = 'id'
            :value = "inputForm.purRequisition.id"
            :limit="1"
            @getValue='(value) => {inputForm.purRequisition.id=value}'
            :columns="[
            {
              prop: 'id',
              label: 'id'
            },
            {
              prop: 'requisitionNumber',
              label: '请购单编号'
            },
            {
              prop: 'requisitionUser',
              label: '请购人'
            }
            ]"
            :searchs="[
            {
              prop: 'id',
              label: 'id'
            },
            {
              prop: 'requisitionNumber',
              label: '请购单编号'
            },
            {
              prop: 'requisitionUser',
              label: '请购人'
            }
            ]"
            dataListUrl="/purchasing/requisition/requisition/list"
            queryEntityUrl="/purchasing/requisition/requisition/queryById">
          </GridSelect>
           </el-form-item>
        </el-col> -->
        <el-col :span="12">
            <el-form-item label="供应商" prop="purSupplier.id"
                :rules="[
                 ]">
          <GridSelect
            title="选择供应商"
            labelName = 'supplierName'
            labelValue = 'id'
            :disabled="true"
            :value = "inputForm.purSupplier.id"
            :limit="1"
            @getValue='(value) => {inputForm.purSupplier.id=value}'
            :columns="[
            {
              prop: 'id',
              label: 'id'
            },
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
            ]"
            :searchs="[
            {
              prop: 'id',
              label: 'id'
            },
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
            ]"
            dataListUrl="/supplier/inventoryset/supplier/list"
            queryEntityUrl="/supplier/inventoryset/supplier/queryById">
          </GridSelect>
           </el-form-item>
        </el-col>
       <!-- <el-col :span="12">
            <el-form-item label="合计含税金额" prop="totalpriceIncludingtax"
                :rules="[
                 ]">
              <el-input v-model="inputForm.totalpriceIncludingtax" placeholder="请填写合计含税金额"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="总数量" prop="totalNumber"
                :rules="[
                 ]">
              <el-input v-model="inputForm.totalNumber" placeholder="请填写总数量"     ></el-input>
           </el-form-item>
        </el-col> -->
    <el-col :span="24">
     <el-form-item label-width="0">
            <el-tabs v-model="sxMeetOrderTab">
            <el-tab-pane label="应付订单详情">
                <!--  <el-button size="small" @click="addSxMeetOrderDetailRow" type="primary">新增</el-button> -->
                  <el-table
                  class="table"
                  size="small"
                  :data="inputForm.sxMeetOrderDetailDTOList.filter(function(item){ return item.delFlag !== '1'})"
                  style="width: 100%">
                  <el-table-column
                    prop="inventory.inventoryName"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="存货">
                  </el-table-column>
                <el-table-column
                    prop="purOrderNum"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="采购数量">
                  </el-table-column>
                <el-table-column
                    prop="purUsedNum"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="已入库数量">
                  </el-table-column>
                <el-table-column
                    prop="purUnusedNum"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="未入库数量">
                  </el-table-column>
                <!-- <el-table-column
                    prop="supplyDate"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="供货时间">
                  </el-table-column> -->
                <el-table-column
                    prop="unitPrice"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="单价">
                  </el-table-column>
                <el-table-column
                    prop="totalPrice"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="总价">
                  </el-table-column>
                <el-table-column
                    prop="received"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="已入庫">
                  </el-table-column>
                <el-table-column
                    prop="payableAmount"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="应付款金额">
                  </el-table-column>
                <el-table-column
                    prop="remainingAmount"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="剩余付款金额">
                  </el-table-column>
                  <el-table-column
                    fixed="right"
                    label="操作"
                    width="150">
                    <template slot-scope="scope">
                      <el-button @click="viewSxMeetOrderDetailRow(scope.row)" type="text" size="small">查看</el-button>
                      <el-button  @click="editSxMeetOrderDetailRow(scope.row)" type="text" size="small">编辑</el-button>
                      <el-button  @click="delSxMeetOrderDetailRow(scope.row)" type="text" size="small">删除</el-button>
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
  <SxMeetOrderDetailForm ref="sxMeetOrderDetailForm" @addRow="saveSxMeetOrderDetailRow(arguments)"></SxMeetOrderDetailForm>
</div>
</template>

<script>
  import SxMeetOrderDetailForm from './SxMeetOrderDetailForm'
  import GridSelect from '@/components/gridSelect'
  import SxMeetOrderService from '@/api/produce/meeto/SxMeetOrderService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        sxMeetOrderTab: '0',
        inputForm: {
          id: '',
          sxMeetOrderDetailDTOList: [],
          purCode: '',
          purContract: {
            id: ''
          },
          purRequisition: {
            id: ''
          },
          purSupplier: {
            id: ''
          },
          procInsId: '',
          procInsResult: '',
          purState: '',
          totalpriceIncludingtax: '',
          totalNumber: ''
        }
      }
    },
    components: {
      GridSelect,
      SxMeetOrderDetailForm
    },
    sxMeetOrderService: null,
    created () {
      this.sxMeetOrderService = new SxMeetOrderService()
    },
    methods: {
      init (method, id) {
        this.method = method
        if (method === 'add') {
          this.title = `新建应付`
        } else if (method === 'edit') {
          this.title = '修改应付'
        } else if (method === 'view') {
          this.title = '查看应付'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.id = id
          this.sxMeetOrderTab = '0'
          this.inputForm.sxMeetOrderDetailDTOList = []
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.sxMeetOrderService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      saveSxMeetOrderDetailRow (child) {
        if (child[0] === '') {
          this.inputForm.sxMeetOrderDetailDTOList.push(child[1])
        } else {
          this.inputForm.sxMeetOrderDetailDTOList.forEach((item, index) => {
            if (item === child[0]) {
              this.inputForm.sxMeetOrderDetailDTOList.splice(index, 1, child[1])
            }
          })
        }
      },
      addSxMeetOrderDetailRow (child) {
        this.$refs.sxMeetOrderDetailForm.init('add')
      },
      viewSxMeetOrderDetailRow (child) {
        this.$refs.sxMeetOrderDetailForm.init('view', child)
      },
      editSxMeetOrderDetailRow (child) {
        this.$refs.sxMeetOrderDetailForm.init('edit', child)
      },
      delSxMeetOrderDetailRow (child) {
        this.inputForm.sxMeetOrderDetailDTOList.forEach((item, index) => {
          if (item === child && item.id === '') {
            this.inputForm.sxMeetOrderDetailDTOList.splice(index, 1)
          } else if (item === child) {
            item.delFlag = '1'
            this.inputForm.sxMeetOrderDetailDTOList.splice(index, 1, item)
          }
        })
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.sxMeetOrderService.save(this.inputForm).then(({data}) => {
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

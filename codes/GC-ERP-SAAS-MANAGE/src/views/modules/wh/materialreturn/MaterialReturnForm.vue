<template>
<div>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible" width="80%">
    <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'"
             label-width="120px">

      <el-row  :gutter="15">

      <el-col :span="12">
            <el-form-item label="物料计划单" prop="materialPlan.id"
                :rules="[
                 ]">
          <GridSelect
            title="选择物料计划单"
            labelName = 'materialplanCode'
            labelValue = 'id'
            :value = "inputForm.materialPlan.id"
            :limit="1"
            @getValue='(value) => {inputForm.materialPlan.id=value}'
            :columns="[
            {
              prop: 'materialplanCode',
              label: '物料计划单号'
            },
            {
              prop: 'createDate',
              label: '物料计划创建时间'
            },
            {
              prop: 'createBy.name',
              label: '物料计划创建人'
            },
            {
              prop: 'produceplan.produceplanCode',
              label: '生产计划单号'
            },
            {
              prop: 'produceplan.createDate',
              label: '生产计划创建时间'
            },
            {
              prop: 'produceplan.createBy.name',
              label: '生产计划创建人'
            },
            {
              prop: 'salesorder.customer.customerName',
              label: '客户名称'
            },
            ]"
            :searchs="[
            {
              prop: 'materialplanCode',
              label: '物料计划单号'
            }
            ]"
            dataListUrl="/produce/materialplan/materialPlan/list"
            queryEntityUrl="/produce/materialplan/materialPlan/queryById">
          </GridSelect>
           </el-form-item>
      </el-col>
        <el-col :span="12">
            <el-form-item label="退料仓库" prop="returnWarehouse.id"
                :rules="[
                  {required: true, message:'退料仓库不能为空', trigger:'blur'}
                 ]">
          <GridSelect
            title="选择退料仓库"
            labelName = 'name'
            labelValue = 'id'
            :value = "inputForm.returnWarehouse.id"
            :limit="1"
            @getValue='(value) => {inputForm.returnWarehouse.id=value}'
            :columns="[
            {
              prop: 'name',
              label: '仓库名称'
            }
            ]"
            :searchs="[
            {
              prop: 'name',
              label: '仓库名称'
            }
            ]"
            dataListUrl="/basic/warehouse/wareHouse/list"
            queryEntityUrl="/basic/warehouse/wareHouse/queryById">
          </GridSelect>
           </el-form-item>
        </el-col>


        <el-col :span="12">
            <el-form-item label="备注" prop="remark"
                :rules="[
                 ]">
              <el-input v-model="inputForm.remark" placeholder="请填写备注"     ></el-input>
           </el-form-item>
        </el-col>
    <el-col :span="24">
     <el-form-item label-width="0">
            <el-tabs v-model="materialReturnTab">
            <el-tab-pane label="退料详情">
                  <el-button size="small" @click="addMaterialReturnDetailRow" type="primary">新增</el-button>
                  <el-table
                  class="table"
                  size="small"
                  :data="inputForm.materialReturnDetailDTOList.filter(function(item){ return item.delFlag !== '1'})"
                  style="width: 100%">
                      <el-table-column prop="inventory.inventoryCode" header-align="center" align="center"
                      show-overflow-tooltip label="物料编码">
                    </el-table-column>
                  <el-table-column
                    prop="inventory.inventoryName"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="物料名称">
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
                <el-table-column
                    prop="num"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="退回数量">
                  </el-table-column>
                  <el-table-column
                    fixed="right"
                    label="操作"
                    width="150">
                    <template slot-scope="scope">
                      <el-button @click="viewMaterialReturnDetailRow(scope.row)" type="text" size="small">查看</el-button>
                      <el-button  @click="editMaterialReturnDetailRow(scope.row)" type="text" size="small">编辑</el-button>
                      <el-button  @click="delMaterialReturnDetailRow(scope.row)" type="text" size="small">删除</el-button>
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
  <MaterialReturnDetailForm ref="materialReturnDetailForm" @addRow="saveMaterialReturnDetailRow(arguments)"></MaterialReturnDetailForm>
</div>
</template>

<script>
  import MaterialReturnDetailForm from './MaterialReturnDetailForm'
  import GridSelect from '@/components/gridSelect'
  import UserSelect from '@/components/userSelect'
  import MaterialReturnService from '@/api/wh/materialreturn/MaterialReturnService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        materialReturnTab: '0',
        inputForm: {
          id: '',
          materialReturnDetailDTOList: [],
          returnWarehouse: {
            id: ''
          },
          materialPlan: {
            id: ''
          },
          returnUser: {
            id: ''
          },
          returnDate: '',
          remark: ''
        }
      }
    },
    components: {
      GridSelect,
      UserSelect,
      MaterialReturnDetailForm
    },
    materialReturnService: null,
    created () {
      this.materialReturnService = new MaterialReturnService()
    },
    methods: {
      init (method, id) {
        this.method = method
        if (method === 'add') {
          this.title = `新建退料`
        } else if (method === 'edit') {
          this.title = '修改退料'
        } else if (method === 'view') {
          this.title = '查看退料'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.id = id
          this.materialReturnTab = '0'
          this.inputForm.materialReturnDetailDTOList = []
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.materialReturnService.queryById(this.inputForm.id).then(({data}) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      saveMaterialReturnDetailRow (child) {
        if (child[0] === '') {
          this.inputForm.materialReturnDetailDTOList.push(child[1])
        } else {
          this.inputForm.materialReturnDetailDTOList.forEach((item, index) => {
            if (item === child[0]) {
              this.inputForm.materialReturnDetailDTOList.splice(index, 1, child[1])
            }
          })
        }
      },
      addMaterialReturnDetailRow (child) {
        this.$refs.materialReturnDetailForm.init(this.inputForm.materialPlan.id, 'add')
      },
      viewMaterialReturnDetailRow (child) {
        this.$refs.materialReturnDetailForm.init(this.inputForm.materialPlan.id, 'view', child)
      },
      editMaterialReturnDetailRow (child) {
        this.$refs.materialReturnDetailForm.init(this.inputForm.materialPlan.id, 'edit', child)
      },
      delMaterialReturnDetailRow (child) {
        this.inputForm.materialReturnDetailDTOList.forEach((item, index) => {
          if (item === child && item.id === '') {
            this.inputForm.materialReturnDetailDTOList.splice(index, 1)
          } else if (item === child) {
            item.delFlag = '1'
            this.inputForm.materialReturnDetailDTOList.splice(index, 1, item)
          }
        })
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.materialReturnService.save(this.inputForm).then(({data}) => {
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



<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible" width="80%">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method==='view'?'readonly':''" :disabled="method==='view'" label-width="120px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="物料出库单编号" prop="materialIssueCode" :rules="[
                 ]">
              <el-input v-model="inputForm.materialIssueCode" placeholder="自动生成" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料计划单" prop="materialPlan.id" :rules="[
               ]">
              <materil-select :limit='1' :value="inputForm.materialPlan.id" @changeValue='selectMaterialPlanChange'
                labelName='materialplanCode' labelValue='id' @getValue='(value) => {inputForm.materialPlan.id=value}'>
              </materil-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料出库类型" prop="materialIssueType" :rules="[
                       ]">
              <el-select v-model="inputForm.materialIssueType" placeholder="请选择" style="width: 100%;">
                <el-option v-for="item in $dictUtils.getDictList('material_issue_type')" :key="item.value"
                  :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label-width="0">
              <el-tabs v-model="materialIssueTab">
                <el-tab-pane label="物料出库详情表">
                  <el-button size="small" @click="addMaterialIssueDetailRow" type="primary"
                    v-if="inputForm.materialPlan.id == ''">新增</el-button>
                  <el-table class="table" size="small"
                    :data="inputForm.materialIssueDetailDTOList.filter(function(item){ return item.delFlag !== '1'})"
                    style="width: 100%">
                    <el-table-column prop="inventory.inventoryName" header-align="center" align="center"
                      show-overflow-tooltip label="存货">
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
                    <el-table-column prop="materialOutNum" header-align="center" align="center" show-overflow-tooltip
                      label="出库数量">
                    </el-table-column>
                    <!-- <el-table-column
                    prop="materialOutHouse.name"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="出库仓库"> -->
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="150">
                      <template slot-scope="scope">
                        <el-button @click="viewMaterialIssueDetailRow(scope.row)" type="text" size="small">查看
                        </el-button>
                        <el-button @click="editMaterialIssueDetailRow(scope.row)" type="text" size="small">编辑
                        </el-button>
                        <el-button @click="delMaterialIssueDetailRow(scope.row)" type="text" size="small">删除</el-button>
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
    <MaterialIssueDetailForm ref="materialIssueDetailForm" @addRow="saveMaterialIssueDetailRow(arguments)">
    </MaterialIssueDetailForm>
  </div>
</template>

<script>
  import MaterialIssueDetailForm from './MaterialIssueDetailForm'
  import GridSelect from '@/components/gridSelect'
  import MaterilSelect from '@/components/materilSelect'
  import MaterialIssueService from '@/api/wh/materialissue/MaterialIssueService'
  import MaterialPlanService from '@/api/produce/materialplan/MaterialPlanService'
  export default {
    data() {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        materialIssueTab: '0',
        inputForm: {
          id: '',
          materialIssueDetailDTOList: [],
          materialIssueCode: '',
          materialPlan: {
            id: ''
          },
          materialIssueState: '',
          materialIssueType: '0'
        }
      }
    },
    components: {
      GridSelect,
      MaterialIssueDetailForm,
      MaterilSelect
    },
    materialIssueService: null,
    created() {
      this.materialIssueService = new MaterialIssueService()
      this.MaterialPlanService = new MaterialPlanService()
    },
    methods: {
      init(method, id) {
        this.method = method
        if (method === 'add') {
          this.title = `新建物料出库`
        } else if (method === 'edit') {
          this.title = '修改物料出库'
        } else if (method === 'view') {
          this.title = '查看物料出库'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.id = id
          this.materialIssueTab = '0'
          this.inputForm.materialIssueDetailDTOList = []
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.materialIssueService.queryById(this.inputForm.id).then(({
              data
            }) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      saveMaterialIssueDetailRow(child) {
        if (child[0] === '') {
          this.inputForm.materialIssueDetailDTOList.push(child[1])
        } else {
          this.inputForm.materialIssueDetailDTOList.forEach((item, index) => {
            if (item === child[0]) {
              this.inputForm.materialIssueDetailDTOList.splice(index, 1, child[1])
            }
          })
        }
      },
      addMaterialIssueDetailRow(child) {
        this.$refs.materialIssueDetailForm.init('add')
      },
      viewMaterialIssueDetailRow(child) {
        this.$refs.materialIssueDetailForm.init('view', child)
      },
      editMaterialIssueDetailRow(child) {
        this.$refs.materialIssueDetailForm.init('edit', child)
      },
      delMaterialIssueDetailRow(child) {
        this.inputForm.materialIssueDetailDTOList.forEach((item, index) => {
          if (item === child && item.id === '') {
            this.inputForm.materialIssueDetailDTOList.splice(index, 1)
          } else if (item === child) {
            item.delFlag = '1'
            this.inputForm.materialIssueDetailDTOList.splice(index, 1, item)
          }
        })
      },
      // 表单提交
      doSubmit() {
        if (this.inputForm.materialIssueDetailDTOList.length < 1) {
          this.$message.error("物料出库详情记录不能为空！")
          return
        }
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.materialIssueService.save(this.inputForm).then(({
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
      selectMaterialPlanChange(data) {
        let dataList = data[0]
        this.MaterialPlanService.queryById(dataList.id).then(({
          data
        }) => {
          let materialIssueDetailDTOList = []
          //debugger
          data.materialPlanDetailDTOList.forEach(item => {
            this.$set(item, 'inventory.inventoryName', item.inventory.inventoryName)
            this.$set(item, 'inventory.inventoryCode', item.inventory.inventoryCode)
            this.$set(item, 'inventory.model', item.inventory.model)
            this.$set(item, 'inventory.singalUnit', item.inventory.singalUnit)
            this.$set(item, 'inventory.unitOfMeasurement', item.inventory.unitOfMeasurement)
            this.$set(item, 'materialOutNum', item.needNum)
            this.$set(item, 'id', '')
            this.$set(item, 'materialOutHouse', item.inventory.inventoryWarehouse.warehouse)
            materialIssueDetailDTOList.push(item)
          })
          this.inputForm.materialIssueDetailDTOList = materialIssueDetailDTOList
        })

      }
    }
  }
</script>

<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" v-dialogDrag :visible.sync="visible">
      <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading"
        :class="method==='view'?'readonly':''" :disabled="method==='view'" label-width="120px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="请购单号" prop="requisitionNumber">
              <el-input v-model="inputForm.requisitionNumber" placeholder="自动生成" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请购人" prop="requisitionUser.id" :rules="[
                  {required: true, message:'请购人不能为空', trigger:'blur'}
                 ]">
              <user-select :limit='1' :value="inputForm.requisitionUser.id"
                @getValue='(value) => {inputForm.requisitionUser.id=value}' @valueChange='userChange'></user-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请购部门" prop="requisitionDep.id" :rules="[
                  {required: true, message:'请购部门不能为空', trigger:'blur'}
                 ]">
              <SelectTree ref="requisitionDep" :props="{
                          value: 'id',             // ID字段名
                          label: 'name',         // 显示名称
                          children: 'children'    // 子级字段名
                        }" url="/sys/office/treeData?type=2" :value="inputForm.requisitionDep.id" :clearable="true"
                :accordion="true" @getValue="(value) => {inputForm.requisitionDep.id=value}" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请购时间" prop="requisitionData" :rules="[
                  {required: true, message:'请购时间不能为空', trigger:'blur'}
                 ]">
              <el-date-picker style="width: 100%;" v-model="inputForm.requisitionData" type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label-width="0">
              <el-tabs v-model="requisitionTab">
                <el-tab-pane label="请购单存货详情">
                  <el-button size="small" @click="addRequisitionDetailRow" type="primary">新增</el-button>
                  <el-table class="table" size="small"
                    :data="inputForm.requisitionDetailDTOList.filter(function(item){ return item.delFlag !== '1'})"
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
                    <el-table-column prop="inventory.inventoryAttribute" header-align="center" align="center" show-overflow-tooltip
                      label="可用库存">
                    </el-table-column>
                    <el-table-column prop="inventoryNum" header-align="center" align="center" show-overflow-tooltip
                      label="请购数量">
                    </el-table-column>
                    <el-table-column prop="inventoryRemark" header-align="center" align="center" show-overflow-tooltip
                      label="请购备注">
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="150">
                      <template slot-scope="scope">
                        <el-button @click="viewRequisitionDetailRow(scope.row)" type="text" size="small">查看</el-button>
                        <el-button @click="editRequisitionDetailRow(scope.row)" type="text" size="small">编辑</el-button>
                        <el-button @click="delRequisitionDetailRow(scope.row)" type="text" size="small">删除</el-button>
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
    <RequisitionDetailForm ref="requisitionDetailForm" @addRow="saveRequisitionDetailRow(arguments)">
    </RequisitionDetailForm>
  </div>
</template>

<script>
  import RequisitionDetailForm from './RequisitionDetailForm'
  import UserSelect from '@/components/userSelect'
  import SelectTree from '@/components/treeSelect/treeSelect.vue'
  import RequisitionService from '@/api/purchasing/requisition/RequisitionService'
  export default {
    data() {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        requisitionTab: '0',
        inputForm: {
          id: '',
          requisitionDetailDTOList: [],
          requisitionNumber: '',
          requisitionUser: {
            id: ''
          },
          requisitionDep: {
            id: ''
          },
          requisitionData: ''
        }
      }
    },
    components: {
      UserSelect,
      SelectTree,
      RequisitionDetailForm
    },
    requisitionService: null,
    created() {
      this.requisitionService = new RequisitionService()
    },
    methods: {
      init(method, id) {
        this.method = method
        if (method === 'add') {
          this.title = `新建请购单`
        } else if (method === 'edit') {
          this.title = '修改请购单'
        } else if (method === 'view') {
          this.title = '查看请购单'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.id = id
          this.requisitionTab = '0'
          this.inputForm.requisitionDetailDTOList = []
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            this.requisitionService.queryById(this.inputForm.id).then(({
              data
            }) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      saveRequisitionDetailRow(child) {
        if (child[0] === '') {
          this.inputForm.requisitionDetailDTOList.push(child[1])
        } else {
          this.inputForm.requisitionDetailDTOList.forEach((item, index) => {
            if (item === child[0]) {
              this.inputForm.requisitionDetailDTOList.splice(index, 1, child[1])
            }
          })
        }
      },
      addRequisitionDetailRow(child) {
        this.$refs.requisitionDetailForm.init('add')
      },
      viewRequisitionDetailRow(child) {
        this.$refs.requisitionDetailForm.init('view', child)
      },
      editRequisitionDetailRow(child) {
        this.$refs.requisitionDetailForm.init('edit', child)
      },
      delRequisitionDetailRow(child) {
        this.inputForm.requisitionDetailDTOList.forEach((item, index) => {
          if (item === child && item.id === '') {
            this.inputForm.requisitionDetailDTOList.splice(index, 1)
          } else if (item === child) {
            item.delFlag = '1'
            this.inputForm.requisitionDetailDTOList.splice(index, 1, item)
          }
        })
      },
      // 表单提交
      doSubmit() {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.requisitionService.save(this.inputForm).then(({
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
      userChange(data) {
        if (data != null && data != '' && data != undefined) {
          this.inputForm.requisitionDep.id = data[0].officeDTO.id
        }
      }
    }
  }
</script>

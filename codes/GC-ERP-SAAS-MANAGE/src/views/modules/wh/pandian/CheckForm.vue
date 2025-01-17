<template>
<div>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
     v-dialogDrag
    :visible.sync="visible" width="80%">
    <el-form :model="inputForm" size="small" ref="inputForm" v-loading="loading" class="readonly"  
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="12">
            <el-form-item label="盘点单号" prop="pdCode">
              <el-input v-model="inputForm.pdCode" placeholder="自动生成" :disabled="true"    ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="盘点仓库" prop="warehouse.id"
                :rules="[
                  {required: true, message:'盘点仓库不能为空', trigger:'blur'}
                 ]">
          <GridSelect
            title="选择盘点仓库"
            labelName = 'name'
            labelValue = 'id'
            :value = "inputForm.warehouse.id"
            :limit="1"
            @getValue='getDetailList'
             :disabled="true" 
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
            <el-form-item label="盘点类型" prop="type"
                :rules="[
                  {required: true, message:'盘点类型不能为空', trigger:'blur'}
                 ]">
               <el-select id="type" v-model="inputForm.type" placeholder="请选择"  :disabled="true" 
                style="width: 100%;">
                <el-option v-for="item in $dictUtils.getDictList('ck_inventory_type')" :key="item.value"
                  :label="item.label" :value="item.value">
                </el-option>
              </el-select>
           </el-form-item>
        </el-col>
        <!-- <el-col :span="12">
            <el-form-item label="盘点状态" prop="status"
                :rules="[
                  {required: true, message:'盘点状态不能为空', trigger:'blur'}
                 ]">
              <el-input v-model="inputForm.status" placeholder="请填写盘点状态"     ></el-input>
           </el-form-item>
        </el-col> -->
        <!-- <el-col :span="12">
            <el-form-item label="盘点人" prop="pandianBy.id"
                :rules="[
                 ]">
                <user-select :limit='1' :value="inputForm.pandianBy.id" @getValue='(value) => {inputForm.pandianBy.id=value}'></user-select>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="盘点时间" prop="pandianDate"
                :rules="[
                 ]">
                <el-date-picker
                      style="width: 100%;"
                      v-model="inputForm.pandianDate"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      placeholder="选择日期时间">
                    </el-date-picker>
           </el-form-item>
        </el-col> -->
    <el-col :span="24">
     <el-form-item label-width="0">
            <el-tabs v-model="pandainTab">
            <el-tab-pane label="盘点单详情">
                  <!-- <el-button size="small" @click="addPandianDetailRow" type="primary">新增</el-button> -->
                  <el-table
                  class="table"
                  size="small"
                  :data="inputForm.pandianDetailDTOList.filter(function(item){ return item.delFlag !== '1'})"
                  style="width: 100%">
               <el-table-column prop="inventory.inventoryCode" header-align="center" align="center" show-overflow-tooltip label="存货编码" >
                    </el-table-column>
                <el-table-column
                    prop="inventory.inventoryName"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="存货">
                  </el-table-column>
                     <el-table-column prop="inventory.model" header-align="center" align="center" show-overflow-tooltip label="规格型号" >
                    </el-table-column>

                   
                    <el-table-column prop="inventory.nventoryAttributeValue" header-align="center" align="center" show-overflow-tooltip label="属性" >
                    </el-table-column>

 <el-table-column prop="inventory.singalUnit" header-align="center" align="center" show-overflow-tooltip label="计量单位" >
                      <template slot-scope="scope">
                        {{ $dictUtils.getDictLabel("sx_metering_unit", scope.row.inventory.singalUnit, '-') }}
                      </template>
                    </el-table-column>
                <el-table-column
                    prop="inventoryInStock"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="在库库存">
                  </el-table-column>
                <el-table-column
                    prop="pandianStock"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="盘点库存">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.pandianStock" type="number"></el-input>
                      </template>
                  </el-table-column>
                <el-table-column
                    prop="remark"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="备注">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.remark"></el-input>
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
      <el-button size="small" type="primary" @click="doSubmit(2)" v-noMoreClick>完成盘点</el-button>
      <el-button size="small" type="primary" @click="doSubmit(1)" v-noMoreClick>继续盘点</el-button>
    </span>
  </el-dialog>
  <PandianDetailForm ref="pandianDetailForm" @addRow="savePandianDetailRow(arguments)"></PandianDetailForm>
</div>
</template>

<script>
  import PandianDetailForm from './PandianDetailForm'
  import GridSelect from '@/components/gridSelect'
  import UserSelect from '@/components/userSelect'
  import PandainService from '@/api/wh/pandian/PandainService'
  import InventoryService from '@/api/basic/inventorytype/InventoryService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        pandainTab: '0',
        inputForm: {
          id: '',
          pandianDetailDTOList: [],
          pdCode: '',
          warehouse: {
            id: ''
          },
          type: '',
          status: '',
          pandianBy: {
            id: ''
          },
          pandianDate: ''
        }
      }
    },
    components: {
      GridSelect,
      UserSelect,
      PandianDetailForm
    },
    pandainService: null,
    inventoryService: null,
    created () {
      this.pandainService = new PandainService()
      this.inventoryService = new InventoryService()
    },
    methods: {
      init (id) {
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.id = id
          this.pandainTab = '0'
          this.inputForm.pandianDetailDTOList = []
          this.loading = true
          this.pandainService.queryById(this.inputForm.id).then(({data}) => {
            this.inputForm = this.recover(this.inputForm, data)
            this.loading = false
          })
        })
      },
      savePandianDetailRow (child) {
        if (child[0] === '') {
          this.inputForm.pandianDetailDTOList.push(child[1])
        } else {
          this.inputForm.pandianDetailDTOList.forEach((item, index) => {
            if (item === child[0]) {
              this.inputForm.pandianDetailDTOList.splice(index, 1, child[1])
            }
          })
        }
      },
      addPandianDetailRow (child) {
        this.$refs.pandianDetailForm.init('add')
      },
      viewPandianDetailRow (child) {
        this.$refs.pandianDetailForm.init('view', child)
      },
      editPandianDetailRow (child) {
        this.$refs.pandianDetailForm.init('edit', child)
      },
      delPandianDetailRow (child) {
        this.inputForm.pandianDetailDTOList.forEach((item, index) => {
          if (item === child && item.id === '') {
            this.inputForm.pandianDetailDTOList.splice(index, 1)
          } else if (item === child) {
            item.delFlag = '1'
            this.inputForm.pandianDetailDTOList.splice(index, 1, item)
          }
        })
      },
      // 表单提交
      doSubmit (status) {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            this.inputForm.status = status
            this.pandainService.save(this.inputForm).then(({data}) => {
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
      getDetailList(data) {
        var _this = this

        _this.inputForm.warehouse.id = data
        _this.inputForm.pandianDetailDTOList = []
        _this.inventoryService.selectList({
          inventoryWarehouse: {warehouse: {id: data}}
        }).then(({
          data
        }) => {
          data.records.forEach((item, index) => {
            _this.inputForm.pandianDetailDTOList.push({'inventory': item, 'inventoryInStock': item.inventoryWarehouse.inventoryInStock})
          })
          _this.loading = false
        })
      }
    }
  }
</script>

  

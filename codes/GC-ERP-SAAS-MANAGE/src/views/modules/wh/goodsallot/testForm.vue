<template>
  <div>

      <el-form
        :model="inputForm"
        size="small"
        ref="inputForm"
        v-loading="loading"
        :class="method === 'view' ? 'readonly' : ''"
        :disabled="method === 'view'"
        label-width="120px"
      >
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item
              label="调出仓库"
              prop="outWarehouse.id"
              :rules="outWarehouseRule"
            >
              <GridSelect
                title="选择调出仓库"
                labelName="name"
                labelValue="id"
                :value="inputForm.outWarehouse.id"
                :limit="1"
                @getValue="
                  (value) => {
                    inputForm.outWarehouse.id = value;
                    inputForm.allotDetailDTOList = [];
                  }
                "
                :columns="[
                  {
                    prop: 'name',
                    label: '调出仓库',
                  },
                ]"
                :searchs="[
                  {
                    prop: 'name',
                    label: '仓库名称',
                  },
                ]"
                dataListUrl="/basic/warehouse/wareHouse/list"
                queryEntityUrl="/basic/warehouse/wareHouse/queryById"
              >
              </GridSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="调入仓库"
              prop="inWarehouse.id"
              :rules="inWarehouseRule"
            >
              <GridSelect
                title="选择调入仓库"
                labelName="name"
                labelValue="id"
                :value="inputForm.inWarehouse.id"
                :limit="1"
                @getValue="
                  (value) => {
                    inputForm.inWarehouse.id = value;
                  }
                "
                :columns="[
                  {
                    prop: 'name',
                    label: '调入仓库',
                  },
                ]"
                :searchs="[
                  {
                    prop: 'name',
                    label: '仓库名称',
                  },
                ]"
                dataListUrl="/basic/warehouse/wareHouse/list"
                queryEntityUrl="/basic/warehouse/wareHouse/queryById"
              >
              </GridSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remark" :rules="[]">
              <el-input
                type="textarea"
                v-model="inputForm.remark"
                placeholder="请填写备注"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label-width="0">
              <el-tabs v-model="warehouseAllotTab">
                <el-tab-pane label="仓库调拨详情">
                  <el-button
                    size="small"
                    @click="addallotDetailRow"
                    type="primary"
                    >新增</el-button
                  >
                  <el-table
                    class="table"
                    size="small"
                    :data="
                      inputForm.allotDetailDTOList.filter(function (item) {
                        return item.delFlag !== '1';
                      })
                    "
                    style="width: 100%"
                  >
                    <el-table-column prop="inventory.inventoryCode" header-align="center" align="center" show-overflow-tooltip label="存货编码" >
                    </el-table-column>
                    <el-table-column prop="inventory.inventoryName" header-align="center" align="center" show-overflow-tooltip label="存货名称" >
                    </el-table-column>
                     <el-table-column prop="num" header-align="center" align="center"  show-overflow-tooltip label="调拨数量" >
                    </el-table-column>
                     <el-table-column prop="inventory.unitOfMeasurement" header-align="center" align="center" show-overflow-tooltip label="在库库存" >
                    </el-table-column>
                    <el-table-column prop="inventory.inventoryAttribute" header-align="center" align="center" show-overflow-tooltip label="可用库存" >
                    </el-table-column>
                    <el-table-column prop="inventory.lockStock" header-align="center" align="center" show-overflow-tooltip label="锁定库存" >
                    </el-table-column>
                   
                    <el-table-column prop="inventory.measurementMethod" header-align="center" align="center" show-overflow-tooltip label="计量方式" >
                     <template slot-scope="scope">
                        {{ $dictUtils.getDictLabel("measurement_method", scope.row.inventory.measurementMethod, '-') }}
                      </template>
                    </el-table-column>
                    <el-table-column prop="inventory.singalUnit" header-align="center" align="center" show-overflow-tooltip label="计量单位" >
                      <template slot-scope="scope">
                        {{ $dictUtils.getDictLabel("sx_metering_unit", scope.row.inventory.singalUnit, '-') }}
                      </template>
                    </el-table-column>
                    <el-table-column prop="inventory.model" header-align="center" align="center" show-overflow-tooltip label="规格型号" >
                    </el-table-column>
                    <el-table-column prop="inventory.brand" header-align="center" align="center" show-overflow-tooltip label="品牌" >
                    </el-table-column>
                    <el-table-column prop="inventory.manufactor" header-align="center" align="center" show-overflow-tooltip label="厂家" >
                    </el-table-column>
                    
                    <el-table-column fixed="right" label="操作" width="150">
                      <template slot-scope="scope">
                        <el-button
                          @click="viewallotDetailRow(scope.row)"
                          type="text"
                          size="small"
                          >查看</el-button
                        >
                        <el-button
                          @click="editallotDetailRow(scope.row)"
                          type="text"
                          size="small"
                          >编辑</el-button
                        >
                        <el-button
                          @click="delallotDetailRow(scope.row)"
                          type="text"
                          size="small"
                          >删除</el-button
                        >
                      </template>
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
              </el-tabs>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="visible = false">关闭</el-button>
        <el-button
          size="small"
          type="primary"
          v-if="method != 'view'"
          @click="doSubmit()"
          v-noMoreClick
          >确定</el-button
        >
      </span> -->
    <allotDetailForm
      ref="allotDetailForm"
      @addRow="saveallotDetailRow(arguments)"
    ></allotDetailForm>
  </div>
</template>

<script>
import allotDetailForm from './AllotDetailForm'
import GridSelect from '@/components/gridSelect'
import WarehouseAllotService from '@/api/wh/goodsallot/WarehouseAllotService'
export default {
  data() {
    var validateOutWarehouse = (rule, value, callback) => {
      var _this = this
      if (value === '') {
        callback(new Error('调出仓库不能为空'))
      } else if (value === _this.inputForm.inWarehouse.id) {
        callback(new Error('调出仓库与调入仓库相同!'))
      } else {
        callback()
      }
    }
    var validateInWarehouse = (rule, value, callback) => {
      var _this = this
      if (value === '') {
        callback(new Error('调入仓库不能为空'))
      } else if (value === _this.inputForm.outWarehouse.id) {
        callback(new Error('调出仓库与调入仓库相同!'))
      } else {
        callback()
      }
    }
    return {
      title: '',
      method: '',
      visible: false,
      loading: false,
      warehouseAllotTab: '0',
      inputForm: {
        id: '',
        allotDetailDTOList: [],
        outWarehouse: {
          id: ''
        },
        inWarehouse: {
          id: ''
        },
        remark: ''
      },
      outWarehouseRule: [
        {
          required: true,
          message: '调出仓库不能为空',
          trigger: 'blur'
        },
        {validator: validateOutWarehouse, trigger: ['blur', 'change']}
      ],
      inWarehouseRule: [
        {
          required: true,
          message: '调入仓库不能为空',
          trigger: 'blur'
        },
        {validator: validateInWarehouse, trigger: ['blur', 'change']}

      ]
    }
  },
  components: {
    GridSelect,
    allotDetailForm
  },
  warehouseAllotService: null,
  created() {
    this.warehouseAllotService = new WarehouseAllotService()
  },
  methods: {
    init(method, id) {
      this.method = method
      if (method === 'add') {
        this.title = `新建仓库调拨`
      } else if (method === 'edit') {
        this.title = '修改仓库调拨'
      } else if (method === 'view') {
        this.title = '查看仓库调拨'
      }
      this.visible = true
      this.loading = false
      this.$nextTick(() => {
        this.$refs.inputForm.resetFields()
        this.inputForm.id = id
        this.warehouseAllotTab = '0'
        this.inputForm.allotDetailDTOList = []
        if (method === 'edit' || method === 'view') {
          // 修改或者查看
          this.loading = true
          this.warehouseAllotService
            .queryById(this.inputForm.id)
            .then(({ data }) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
        }
      })
    },
    saveallotDetailRow(child) {
      if (child[0] === '') {
        this.inputForm.allotDetailDTOList.push(child[1])
      } else {
        this.inputForm.allotDetailDTOList.forEach((item, index) => {
          if (item === child[0]) {
            this.inputForm.allotDetailDTOList.splice(index, 1, child[1])
          }
        })
      }
    },
    addallotDetailRow(child) {
      if (this.inputForm.outWarehouse.id) {
        this.$refs.allotDetailForm.init(this.inputForm.outWarehouse.id, 'add')
      } else {
        this.$message.error('请选择调出仓库')
      }
    },
    viewallotDetailRow(child) {
      this.$refs.allotDetailForm.init(
        this.inputForm.outWarehouse.id,
        'view',
        child
      )
    },
    editallotDetailRow(child) {
      this.$refs.allotDetailForm.init(
        this.inputForm.outWarehouse.id,
        'edit',
        child
      )
    },
    delallotDetailRow(child) {
      this.inputForm.allotDetailDTOList.forEach((item, index) => {
        if (item === child && item.id === '') {
          this.inputForm.allotDetailDTOList.splice(index, 1)
        } else if (item === child) {
          item.delFlag = '1'
          this.inputForm.allotDetailDTOList.splice(index, 1, item)
        }
      })
    },
     // 表单提交
    saveForm (callback) {
      this.$refs['inputForm'].validate((valid) => {
        if (valid) {
          this.loading = true
          this.warehouseAllotService
            .save(this.inputForm)
            .then(({data}) => {
              callback(data.businessTable, data.businessId, this.inputForm)
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

  

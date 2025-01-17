<template>
<div>
    <el-input placeholder="请选择" :size="size" :disabled="disabled"  :readonly="readonly" style="line-hight:40px" v-model="name" class="input-with-select">
      <el-button slot="append" :disabled="disabled"  :readonly="readonly" @click="showSelect" icon="el-icon-search"></el-button>
    </el-input>
    <inventory-select-dialog ref="selectDialog" @doSubmit="selectToInput" :limit="limit" :selectData="selectData"  :params="params"></inventory-select-dialog>
</div>
</template>
<script>
import InventorySelectDialog from './inventorySelectDialog.vue'
import InventoryService from '@/api/basic/inventorytype/InventoryService'
export default {
  data () {
    return {
      name: '',
      labelValue: this.value,
      selectData: []
    }
  },
  props: {
    params: {},
    limit: Number,
    value: String,
    size: {
      type: String,
      default: () => { return 'small' }
    },
    readonly: {
      type: Boolean,
      default: () => { return false }
    },
    disabled: {
      type: Boolean,
      default: () => { return false }
    }
  },
  components: {
    InventorySelectDialog
  },
  inventoryService: null,
  beforeCreate () {
    this.inventoryService = new InventoryService()
  },
  watch: {
    value: {
      handler (newVal) {
        this.selectData = []
        if (newVal) {
          newVal.split(',').forEach((id) => {
            this.inventoryService.findByWarehouse(id).then(({data}) => {
              if (data && data.id !== '') {
                this.selectData.push(data)
              }
            })
          })
        }
      },
      immediate: true,
      deep: false
    },
    selectData: {
      handler (newVal) {
        this.name = newVal.map(data => { return data.inventoryName }).join(',')
      },
      immediate: false,
      deep: false
    }
  },
  methods: {
    selectToInput (datas) {
      this.selectData = datas
      this.labelValue = datas.map(data => { return data.id }).join(',')
      this.WarehouseValue = datas.map(data => { return data.inventoryWarehouse.id }).join(',')
      this.name = datas.map(data => { return data.inventoryName }).join(',')
      this.$emit('getValue', this.labelValue, this.name, this.WarehouseValue)
      this.$emit('changeValue', datas)
      // 多选数据-产品出库调用
      this.$emit('valueChanges', datas, this)
    },
    showSelect () {
      this.$refs.selectDialog.init()
    }
  }
}
</script>
<style>
  .el-form-item__content .el-input-group {
      vertical-align: middle;
  }
 .el-tag + .el-tag {
    margin-left: 5px;
    margin-bottom: 5px;
  }
</style>



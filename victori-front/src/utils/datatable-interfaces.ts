import { KeyPair } from "./global-interfaces"
import { EOrderType } from "./global-types"

export interface IStatus {
    item: KeyPair,
    isLoading?: boolean
}
export interface IContextMenu {
    display: boolean,
    style: Object,
    rowIndexes: Array<number>,
    //items: Map<string, IStatus>
}

export interface ITableConfig {
    headerColumns: Array<IColumn>,
    tableRows: Array<Object>,
    headerExtraColumns?: Array<IColumn>,
    isSearchable?: boolean,
    isSortable?: boolean,
    maxHeight?: string
}

export interface ITableIndexes {
    tableRowIndexes: Array<number>,
    expandedRowIndexes?: Array<number>,
    pageRowIndexes?: Array<number>,
    selectedRowIndexes?: Array<number>,
    filteredRowIndexes?: Array<number>,
}

export interface IColumnConfig {
    onReOrder: (header: { headerColumns: Array<IColumn>, extraColumns?: Array<IColumn> }) => void,
    onReOrganize?: (header: { headerColumns: Array<IColumn>, extraColumns?: Array<IColumn> }) => void
}

export interface ICallBacks {
    onDeleteConfig?: IConfig,
    onClickConfig?: IConfig,
    onEditConfig?: IConfig,
    onLoadMoreCallBack?: () => void,
    columnConfig?: IColumnConfig
}

export interface IConfig {
    isMultiRow?: boolean,
    callBack: (rows: Array<Object>) => void
}

export interface IPaginationConfig {
    entry?: number,
    entries?: Array<number>,
}

export interface IColumn {
    key: string,
    value: string,
    type: string,
    minWidth: number
}

export interface ISort {
    columnIndex: number,
    order: EOrderType,
}

export interface IColorConfig {
    color: string,
    background: string,
}

export interface IPagination {
    config?: IPaginationConfig,
    pages?: Array<number>,
    pageIndex?: number
}

export interface ITable {
    title?: string,
    config: ITableConfig,
    indexes: ITableIndexes,
}

export interface IDataTable {
    table: ITable,
    callBacks: ICallBacks,
    colorConfig: IColorConfig,
    contextMenu?: IContextMenu,
    search?: string,
    sort?: ISort,
    pagination?: IPagination
}
import { PayloadAction } from "@reduxjs/toolkit";
import { DataTableProps } from "../../components/DataTable/DataTable";
import { ORDERS } from "../../utils/Constants";
import { IColumn, IDataTable, IPagination, IPaginationConfig, ISort, IStatus } from "../../utils/datatable-interfaces";
import { defaultColorConfig, getObjectValueStr, getSlicedRows, getValue, computePages } from "../../utils/datatable-util";
import { isEqual } from "../../utils/global-util";
import { IContextMenu } from "../../utils/datatable-interfaces";
import { IDataTableState } from "../reducers/data-table-reducer";
import { DEFAULTENTRIES, ITEMACTIONS } from "../../utils/datatable-constants";

export const dataTableActions = {
    initDataTable: (state: IDataTableState,
        action: PayloadAction<DataTableProps>) => {

        const payload = action.payload;
        const id = payload.UID;

        const data = {
            table: {
                title: payload.title,
                config: payload.tableConfig,
                indexes: { tableRowIndexes: Object.keys(payload.tableConfig.tableRows).map(index => parseInt(index)) }
            },
            colorConfig: payload.colorConfig || defaultColorConfig,
            callBacks: {
                onDeleteConfig: payload.onDeleteConfig,
                onClickConfig: payload.onClickConfig,
                onEditConfig: payload.onEditConfig,
                onLoadMoreCallBack: payload.onLoadMoreCallBack,
            },
        } as IDataTable;

        let paginationConfig = payload.paginationConfig;
        if (paginationConfig) {
            const entries = paginationConfig.entries ? paginationConfig.entries : DEFAULTENTRIES;
            if (!paginationConfig.entry) {
                paginationConfig.entry = entries[0];
            }
            data.pagination = {
                config: paginationConfig,
                pageIndex: 0,
                pages: [1],
            }
        }

        if (payload.columnConfig) {
            data.callBacks.columnConfig = payload.columnConfig;
        }

        state[id] = data;
        return state;
    },
    updateEntry: (state: IDataTableState,
        action: PayloadAction<{ UID: string, entry: number }>) => {

        const id = action.payload.UID;
        const pagination = { ...state[id].pagination } as IPagination;
        const paginationConfig = { ...pagination.config } as IPaginationConfig;
        paginationConfig.entry = action.payload.entry;

        pagination.config = paginationConfig;
        pagination.pageIndex = 0;
        state[id].pagination = pagination;
        return state;
    },

    updateRows: (state: IDataTableState,
        action: PayloadAction<{ UID: string, rows: Array<Object> }>) => {

        const id = action.payload.UID;
        const rows = action.payload.rows;
        state[id].table.config.tableRows = rows;
        state[id].table.indexes.tableRowIndexes = Object.keys(rows).map(index => parseInt(index));
        return state;
    },

    performSearch: (state: IDataTableState,
        action: PayloadAction<{ UID: string, search?: string }>) => {

        const id = action.payload.UID;
        const search = action.payload.search === undefined ? state[id].search : action.payload.search;

        if (!search?.length) {
            state[id].search = "";
            delete state[id].table.indexes.filteredRowIndexes;
        }
        else {
            const headerExtraColumns = state[id].table.config.headerExtraColumns || [];
            const tableRowIndexes = state[id].table.indexes.tableRowIndexes;
            const headerColumns = state[id].table.config.headerColumns;
            state[id].table.indexes.filteredRowIndexes = tableRowIndexes.filter((index) => {
                const row = state[id].table.config.tableRows[index];
                return headerColumns.concat(headerExtraColumns).some((column) =>
                    getObjectValueStr(row, column).toLowerCase().includes(search.toLowerCase())
                )
            });
            state[id].search = search;
        };
        const pagination = { ...state[id].pagination, pageIndex: 0 }
        state[id].pagination = pagination;
        return state;
    },

    selectRows: (state: IDataTableState,
        action: PayloadAction<{ UID: string, rowIndexes: Array<number> }>) => {

        const id = action.payload.UID;
        const rowIndexes = action.payload.rowIndexes.concat(state[id].table.indexes.selectedRowIndexes || []);
        if (rowIndexes.length === 0) {
            delete state[id].table.indexes.selectedRowIndexes;
        }
        else {
            state[id].table.indexes.selectedRowIndexes = [...new Set(rowIndexes)];
        }
        return state;
    },

    unSelectRows: (state: IDataTableState,
        action: PayloadAction<{ UID: string, rowIndexes: Array<number> }>) => {

        const id = action.payload.UID;
        const rowIndexes = action.payload.rowIndexes;

        const copyRows = state[id].table.indexes.selectedRowIndexes || [];
        const filtered = copyRows.filter(index => !rowIndexes.includes(index));

        if (filtered.length) {
            state[id].table.indexes.selectedRowIndexes = filtered;
        }
        else {
            delete state[id].table.indexes.selectedRowIndexes;
        }
        return state;
    },


    reverseRows: (state: IDataTableState,
        action: PayloadAction<{ UID: string, sort: ISort }>) => {

        const id = action.payload.UID;
        const filteredRowIndexes = state[id].table.indexes.filteredRowIndexes || [];
        if (filteredRowIndexes.length) {
            filteredRowIndexes.reverse();
            state[id].table.indexes.filteredRowIndexes = filteredRowIndexes;
        }
        else {
            const tableRowIndexes = state[id].table.indexes.tableRowIndexes;
            tableRowIndexes.reverse();
            state[id].table.indexes.tableRowIndexes = tableRowIndexes;
        }
        state[id].sort = action.payload.sort;
        return state;
    },

    performSort: (state: IDataTableState,
        action: PayloadAction<{ UID: string, sort?: ISort }>) => {

        const id = action.payload.UID;
        const newSort = action.payload.sort || state[id].sort;

        const tableRowIndexes = [...state[id].table.indexes.tableRowIndexes];
        const headerColumns = state[id].table.config.headerColumns;
        const tableRows = state[id].table.config.tableRows;
        newSort && tableRowIndexes.sort((row1, row2) => {
            const row1Value = getValue(tableRows[row1], headerColumns[newSort.columnIndex]);
            const row2Value = getValue(tableRows[row2], headerColumns[newSort.columnIndex]);
            const row1ToCompare = typeof row1Value === "string" ? row1Value.toLowerCase() : row1Value;
            const row2ToCompare = typeof row2Value === "string" ? row2Value.toLowerCase() : row2Value;

            if (newSort.order === ORDERS.ASC) return row1ToCompare < row2ToCompare ? -1 : 1;
            if (newSort.order === ORDERS.DESC) return row1ToCompare > row2ToCompare ? -1 : 1;
            return 0;
        });

        state[id].table.indexes.tableRowIndexes = tableRowIndexes;
        state[id].sort = newSort;
        return state;
    },

    closeContextMenu: (state: IDataTableState,
        action: PayloadAction<{ UID: string }>) => {

        if (state[action.payload.UID].contextMenu) {
            delete state[action.payload.UID].contextMenu;
            return state;
        }
    },

    createContextMenu: (state: IDataTableState,
        action: PayloadAction<{ UID: string, style: Object, rowIndexes: Array<number> }>) => {

        const id = action.payload.UID;
        const style = action.payload.style;
        const rowIndexes = action.payload.rowIndexes;

        const contextMenu = { ...state[id].contextMenu } as IContextMenu;
        if (isEqual(rowIndexes, contextMenu.rowIndexes)) {
            state[id].contextMenu = { ...contextMenu, style };
        }
        else {
            state[id].contextMenu = {
                ...contextMenu,
                display: true,
                style,
                rowIndexes
            };
        }
        return state;
    },

    setPageRowIndexes: (state: IDataTableState,
        action: PayloadAction<{ UID: string, pageRowIndexes: Array<number> }>) => {

        state[action.payload.UID].table.indexes.pageRowIndexes = action.payload.pageRowIndexes
        return state;
    },

    updatePage: (state: IDataTableState,
        action: PayloadAction<{ UID: string }>) => {

        const id = action.payload.UID;
        const filteredRowIndexes = state[id].table.indexes.filteredRowIndexes;
        const tableRowIndexes = state[id].table.indexes.tableRowIndexes;
        const entry = state[id].pagination?.config?.entry || DEFAULTENTRIES[0];

        state[id].table.indexes.pageRowIndexes = getSlicedRows(filteredRowIndexes || tableRowIndexes, state[id].pagination?.pageIndex || 0, entry);
        const pages = computePages(filteredRowIndexes?.length || tableRowIndexes.length, entry);

        const pagination = { ...state[id].pagination, pages }
        state[id].pagination = pagination;

        return state;
    },

    setPageIndex: (state: IDataTableState,
        action: PayloadAction<{ UID: string, pageIndex: number }>) => {

        const id = action.payload.UID;
        const pageIndex = action.payload.pageIndex;
        const pages = state[id].pagination?.pages || [];

        if (pageIndex < pages.length && pageIndex >= 0) {
            const pagination = { ...state[id].pagination, pageIndex }
            state[id].pagination = pagination;
        }
        return state;
    },

    toggleExpandRow: (state: IDataTableState,
        action: PayloadAction<{ UID: string, rowIndex: number }>) => {

        const id = action.payload.UID;
        const rowIndex = action.payload.rowIndex;

        const copyRows = state[id].table.indexes.expandedRowIndexes || [];

        if (copyRows.includes(rowIndex)) {
            const filtered = copyRows.filter(index => index !== rowIndex);
            if (!filtered.length) {
                delete state[id].table.indexes.expandedRowIndexes;
            }
            else {
                state[id].table.indexes.expandedRowIndexes = filtered;
            }
        }
        else {
            copyRows.push(rowIndex)
            state[id].table.indexes.expandedRowIndexes = copyRows;
        }
        return state;
    },

    updateColumns: (state: IDataTableState,
        action: PayloadAction<{ UID: string, headerColumns: Array<IColumn>, headerExtraColumns: Array<IColumn> }>) => {

        const id = action.payload.UID;
        state[id].table.config.headerColumns = action.payload.headerColumns;
        state[id].table.config.headerExtraColumns = action.payload.headerExtraColumns;
        return state;
    },
}
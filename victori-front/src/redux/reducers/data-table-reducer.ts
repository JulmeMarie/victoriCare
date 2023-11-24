import { createSlice } from '@reduxjs/toolkit';
import { IDataTable } from '../../utils/datatable-interfaces';
import { dataTableActions } from '../actions/data-table-actions';

export interface IDataTableState {
    [uniqId: string]: IDataTable
}

export const dataTableSlice = createSlice({
    name: "datatableReducer",
    initialState: {} as IDataTableState,
    reducers: dataTableActions
});

export const {
    initDataTable,
    updateEntry,
    performSearch,
    performSort,
    reverseRows,
    createContextMenu,
    closeContextMenu,
    selectRows,
    unSelectRows,
    setPageRowIndexes,
    updatePage,
    setPageIndex,
    toggleExpandRow,
    updateRows,
    updateColumns
} = dataTableSlice.actions;
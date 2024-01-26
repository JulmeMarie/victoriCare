import React, { FC, useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { initDataTable, performSort, performSearch, closeContextMenu, updatePage, setPageIndex, updateRows } from '../../redux/reducers/data-table-reducer';
import { AppDispatch } from '../../redux/store';
import { DEFAULTENTRIES } from '../../utils/datatable-constants';
import { IColorConfig, IColumnConfig, IConfig, IPaginationConfig, ITableConfig } from '../../utils/datatable-interfaces';
import { defaultColorConfig } from '../../utils/datatable-util';
import ColumnFilter from './ColumnFilter/ColumnFilter';
import './DataTable.css';
import EntryFilter from './EntryFilter/EntryFilter';
import Pagination from './Pagination/Pagination';
import SearchFilter from './SearchFilter/SearchFilter';
import Table from './Table/Table';

export interface DataTableProps {
  UID: string,
  title?: string,
  tableConfig: ITableConfig,
  colorConfig?: IColorConfig,
  columnConfig?: IColumnConfig,
  paginationConfig?: IPaginationConfig,
  onDeleteConfig?: IConfig,
  onClickConfig?: IConfig,
  onEditConfig?: IConfig,
  onLoadMoreCallBack: () => void
}

const DataTable: FC<DataTableProps> = (props) => {
  const dispatch = useDispatch<AppDispatch>();
  const UID = props.UID;
  const isSortable = props.tableConfig?.isSortable;
  const isSearchable = props.tableConfig?.isSearchable;
  const colorConfig = props.colorConfig || defaultColorConfig;
  const isPaginable = props.paginationConfig !== undefined;
  const entries = props.paginationConfig?.entries || DEFAULTENTRIES;
  const rows = props.tableConfig.tableRows;
  const [initialized, setInitialized] = useState<boolean>(false);

  console.log("DataTable")

  useEffect(() => {
    initialized ? dispatch(updateRows({ UID, rows })) : dispatch(initDataTable(props));
    isSortable && dispatch(performSort({ UID }));
    isSearchable && dispatch(performSearch({ UID }));
    dispatch(setPageIndex({ UID, pageIndex: 0 }));
    dispatch(updatePage({ UID }));
    setInitialized(true);
  }, [rows]);

  return (
    <div
      className="DataTable"
      data-testid="DataTable"
      id={`Datatable-${UID}`}
      style={{ border: `solid ${colorConfig.background}  0.01rem`, color: colorConfig.color }}
      onClick={() => { dispatch(closeContextMenu({ UID })) }}
    >
      {!initialized && <div className='waiting-message'>Table en cours d'initialisation...</div>}
      {initialized &&
        <>
          {props.title && <h3 className='Datatable__title'> {props.title}</h3>}
          {(isSearchable || isPaginable || props.columnConfig) &&
            <div className='DataTable_filter'>
              {entries &&
                <EntryFilter
                  UID={UID}
                  colorConfig={colorConfig}
                />
              }
              {props.columnConfig && <ColumnFilter UID={UID} />}
              {isSearchable &&
                <SearchFilter
                  UID={UID}
                  colorConfig={colorConfig} />
              }
            </div>
          }
          <div className='horizontal-scroll-bar xm custom-scroll-bar DataTable-wrapper'><Table UID={UID} /></div>
          {isPaginable && <Pagination UID={UID} />}
        </>}
    </div>
  );
}

export default DataTable;
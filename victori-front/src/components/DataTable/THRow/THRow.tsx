import React, { FC, useMemo } from 'react';
import { FaCheckCircle, FaRegSquare, FaSort, FaSortDown, FaSortUp } from 'react-icons/fa';
import { useDispatch, useSelector } from 'react-redux';
import { defaultColorConfig } from '../../../utils/datatable-util';
import { AppDispatch, AppState } from '../../../redux/store';
import { ORDERS } from '../../../utils/Constants';
import { IContextMenu } from '../../../utils/datatable-interfaces';
import './THRow.css';
import { selectRows, unSelectRows } from '../../../redux/reducers/data-table-reducer';
interface THRowProps {
  UID: string,
  showCheckBox: boolean,
  onContextMenu: (e: React.MouseEvent<HTMLElement>, rows?: Array<number>) => void
  handleSortCallBack: (columnIndex: number) => void
}

const THRow: FC<THRowProps> = ({ UID, showCheckBox, onContextMenu, handleSortCallBack }) => {
  const dispatch = useDispatch<AppDispatch>();
  const datatable = useSelector((state: AppState) => state.datatableReducer[UID]) || {};
  const sort = datatable.sort;
  const headers = datatable.table.config.headerColumns || [];
  const extraColumns = datatable.table.config.headerExtraColumns;
  const contextMenu = datatable.contextMenu || {} as IContextMenu;
  const colorConfig = datatable.colorConfig || defaultColorConfig;

  const isSelected = useMemo(() => {
    const selectedRowIndexes = datatable.table.indexes.selectedRowIndexes;
    const pageRowIndexes = datatable.table.indexes.pageRowIndexes;
    if (!pageRowIndexes?.length) return false;
    if (!selectedRowIndexes?.length) return false;
    return pageRowIndexes.every(pIndex => selectedRowIndexes.includes(pIndex));

  }, [datatable.table.indexes.selectedRowIndexes, datatable.table.indexes.pageRowIndexes]);

  console.log("THRow");

  const onToggleSelect = (event: React.MouseEvent<HTMLElement>) => {
    if (!contextMenu.display) {
      event.stopPropagation();
      const rowIndexes = datatable.table.indexes.pageRowIndexes;
      if (rowIndexes) {
        isSelected ? dispatch(unSelectRows({ UID, rowIndexes })) : dispatch(selectRows({ UID, rowIndexes }));
      }
    }
  }

  return (
    <tr onContextMenu={(event) => { onContextMenu(event) }}>
      {extraColumns && extraColumns.length > 0 && <th
        style={{ ...colorConfig }}
        className='show-details-box'
      >
      </th>}
      {headers.map((column, index) => {
        return <th
          key={index}
          className={column.key}
          style={{ ...colorConfig, minWidth: `${column.minWidth}px` }}
          onClick={event => {
            if (!contextMenu.display) {
              event.stopPropagation();
              handleSortCallBack(index)
            }
          }}
        >
          {datatable.table.config.isSortable &&
            <div className='th-icons'>
              {(index !== sort?.columnIndex) && <FaSort />}
              {(index === sort?.columnIndex && sort.order === ORDERS.ASC) && <FaSortUp />}
              {(index === sort?.columnIndex && sort.order === ORDERS.DESC) && <FaSortDown />}
            </div>}
          <div className='th-text'>  {column.value} </div>
        </th>
      })
      }
      {showCheckBox &&
        <th
          style={{ ...colorConfig }}
          className='th-checkbox'
          onClick={(event) => onToggleSelect(event)}
        >
          {isSelected ? <FaCheckCircle /> : <FaRegSquare />}
        </th>
      }
    </tr >
  );
}
export default THRow;
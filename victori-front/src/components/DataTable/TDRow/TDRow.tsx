import React, { FC, useMemo } from 'react';
import { FaCheckCircle, FaCube, FaMinusCircle, FaPlusCircle, FaRegSquare } from 'react-icons/fa';
import { useDispatch, useSelector } from 'react-redux';
import { defaultColorConfig } from '../../../utils/datatable-util';
import { AppDispatch, AppState } from '../../../redux/store';
import { getObjectValueStr } from '../../../utils/datatable-util';
import './TDRow.css';
import { closeContextMenu, selectRows, toggleExpandRow, unSelectRows } from '../../../redux/reducers/data-table-reducer';
import { IContextMenu } from '../../../utils/datatable-interfaces';
import { isNullOrEmpty } from '../../../utils/global-util';

interface RowProps {
  rowIndex: number;
  showCheckBox: boolean,
  UID: string,
  onContextMenu: (e: React.MouseEvent<HTMLElement>, rows: Array<number>) => void
}

const TDRow: FC<RowProps> = ({ UID, showCheckBox, rowIndex, onContextMenu }) => {
  const dispatch = useDispatch<AppDispatch>();
  const datatable = useSelector((state: AppState) => state.datatableReducer[UID]) || {};
  const tableRows = datatable.table.config?.tableRows || [];
  const headers = datatable.table.config.headerColumns || [];
  const contextMenu = datatable.contextMenu || {} as IContextMenu;
  const colorConfig = datatable.colorConfig || defaultColorConfig;
  const headerExtraColumns = datatable.table.config.headerExtraColumns;

  const isSelected = useMemo(() => {
    return showCheckBox && (datatable.table.indexes.selectedRowIndexes || []).includes(rowIndex);
  }, [datatable.table.indexes.selectedRowIndexes, rowIndex]);

  const isExpanded = useMemo(() => {
    return (datatable.table.indexes.expandedRowIndexes || []).includes(rowIndex);
  }, [datatable.table.indexes.expandedRowIndexes, rowIndex]);


  const onExpandRow = (e: React.MouseEvent<HTMLElement>) => {
    e.stopPropagation();
    contextMenu.display ?
      dispatch(closeContextMenu({ UID })) :
      dispatch(toggleExpandRow({ UID, rowIndex }));
  };

  const onSelect = (event: React.MouseEvent<HTMLElement>) => {
    event.stopPropagation();
    if (contextMenu.display) {
      dispatch(closeContextMenu({ UID }));
    }
    else {
      isSelected ?
        dispatch(unSelectRows({ UID, rowIndexes: [rowIndex] })) :
        dispatch(selectRows({ UID, rowIndexes: [rowIndex] }))
    }
  }

  return (
    <>
      <tr
        style={{ color: colorConfig.color }}
        className={` ${datatable.callBacks.onClickConfig && 'pointer'}`}
        //onClick={(event) => { onContextMenu(event, [rowIndex]) }}
        onContextMenu={(event) => { onContextMenu(event, [rowIndex]) }}
      >
        {headerExtraColumns && headerExtraColumns.length > 0 &&
          <td
            className='show-details-box'
            onClick={(event) => onExpandRow(event)}
            style={{ color: `${colorConfig.color}` }} >
            {isExpanded ? <FaMinusCircle /> : <FaPlusCircle />}
          </td>}
        {headers.map((column, indexColumn) =>
          <td
            key={indexColumn}
            style={{ minWidth: column.minWidth + 'px' }}
          >
            {getObjectValueStr(tableRows[rowIndex], column)}
          </td>)
        }
        {showCheckBox &&
          <td
            style={{ color: `${colorConfig.color}` }}
            className='td-checkbox'
            onClick={(event) => onSelect(event)}>
            {isSelected ? <FaCheckCircle /> : <FaRegSquare />}
          </td>
        }
      </tr>
      {!isNullOrEmpty(headerExtraColumns) && isExpanded &&
        <tr>
          <td colSpan={headers.length + (showCheckBox ? 1 : 0)}>
            <table className='inner-table'>
              <tbody>
                {(headerExtraColumns || []).map((column, index) =>
                  <tr key={index}>
                    <th > {column.value}</th>
                    <td style={{ width: column.minWidth + 'px' }}>
                      {getObjectValueStr(tableRows[rowIndex], column)}
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </td>
        </tr>
      }
    </>
  );
}
export default TDRow;
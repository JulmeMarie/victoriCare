import React, { FC } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { reverseRows, createContextMenu, updatePage } from '../../../redux/reducers/data-table-reducer';
import { AppDispatch, AppState } from '../../../redux/store';
import './Table.css';
import { ORDERS } from '../../../utils/Constants';
import { IContextMenu } from '../../../utils/datatable-interfaces';
import TDRow from '../TDRow/TDRow';
import THRow from '../THRow/THRow';
import ContextMenu from '../ContextMenu/ContextMenu';
import { ISort } from '../../../utils/datatable-interfaces';
import { isNullOrEmpty } from '../../../utils/global-util';
interface TableProps {
  UID: string
}

const Table: FC<TableProps> = ({ UID }) => {
  const dispatch = useDispatch<AppDispatch>();
  const datatable = useSelector((state: AppState) => state.datatableReducer[UID]) || {};
  const maxHeight = datatable.table.config.maxHeight;
  const pageRowIndexes = datatable.table?.indexes?.pageRowIndexes || [];
  const headersLength = datatable.table?.config?.headerColumns.length || 0;
  const contextMenu = datatable.contextMenu || {} as IContextMenu;
  const showCheckBox = datatable.callBacks.onDeleteConfig?.isMultiRow || datatable.callBacks.onEditConfig?.isMultiRow || datatable.callBacks.onClickConfig?.isMultiRow || false;

  const sort = datatable.sort;

  console.log("Table");

  const onSort = (columnIndex: number) => {
    if (!datatable.table.config?.isSortable || contextMenu.display) return;

    const newSort = { order: ORDERS.ASC, columnIndex } as ISort;
    if (newSort.columnIndex === sort?.columnIndex) {//check if column is the same as current sorted column
      newSort.order = sort?.order === ORDERS.ASC ? ORDERS.DESC : ORDERS.ASC;
    }
    dispatch(reverseRows({ UID, sort: newSort }));
    dispatch(updatePage({ UID }));
  }

  const onContextMenu = (event: React.MouseEvent<HTMLElement>, rowIndexes?: Array<number>) => {
    event.preventDefault();
    event.stopPropagation();
    const div = document.querySelector(".DataTable");
    const rec = div?.getBoundingClientRect() || { left: 0, top: 0 };
    const style = {
      left: (event.clientX - rec.left) + "px",
      top: (event.clientY - rec.top) + "px"
    };
    dispatch(createContextMenu({ UID, style, rowIndexes: rowIndexes ? rowIndexes : pageRowIndexes }))
  }

  return (
    <>
      <table className="Table" data-testid="Table"  >
        {headersLength > 0 &&
          <thead>
            <THRow
              UID={UID}
              showCheckBox={showCheckBox}
              handleSortCallBack={onSort}
              onContextMenu={onContextMenu} />
          </thead>
        }
        <tbody style={{ maxHeight: maxHeight }} className='custom-scroll-bar xs vertical-scroll-bar'>
          {pageRowIndexes.map((rowIndex, index) =>
            <TDRow
              UID={UID}
              showCheckBox={showCheckBox}
              rowIndex={rowIndex}
              onContextMenu={onContextMenu}
              key={index} />)
          }
          {isNullOrEmpty(pageRowIndexes) &&
            <tr>
              <td
                className='td-no-result'
                colSpan={headersLength + +showCheckBox}>
                No matching records found
              </td>
            </tr>
          }
        </tbody>
        {!isNullOrEmpty(pageRowIndexes) &&
          <tfoot>
            <THRow
              UID={UID}
              showCheckBox={showCheckBox}
              handleSortCallBack={onSort}
              onContextMenu={onContextMenu}
            />
          </tfoot>
        }
      </table>
      {contextMenu.display && <ContextMenu UID={UID} />}
    </>
  );
}
export default Table;
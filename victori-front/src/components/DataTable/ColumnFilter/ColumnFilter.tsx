import React, { FC, useState } from 'react';
import { FaGripHorizontal } from 'react-icons/fa';
import { useDispatch, useSelector } from 'react-redux';
import { updateColumns } from '../../../redux/reducers/data-table-reducer';
import { AppDispatch, AppState } from '../../../redux/store';
import { DIRECTIONS } from '../../../utils/Constants';
import { IColumn } from '../../../utils/datatable-interfaces';
import { defaultColorConfig } from '../../../utils/datatable-util';
import { KeyPair } from '../../../utils/global-interfaces';
import { isMultiple } from '../../../utils/global-util';
import DragableBox from '../DragableBox/DragableBox';
import './ColumnFilter.css';

interface ColumnFilterProps {
  UID: string
}

const ColumnFilter: FC<ColumnFilterProps> = ({ UID }) => {
  const dispatch = useDispatch<AppDispatch>();
  const datatable = useSelector((state: AppState) => state.datatableReducer[UID]) || {};
  const colorConfig = datatable.colorConfig || defaultColorConfig;
  const onReOrder = datatable.callBacks?.columnConfig?.onReOrder;
  const onReOrganize = datatable.callBacks?.columnConfig?.onReOrganize;
  const headerColumns = datatable.table.config.headerColumns;
  const headerExtraColumns = datatable.table.config.headerExtraColumns || [];
  const [isOpen, setIsOpen] = useState(false);

  const handleMoveToExtrasColumns = (key: string) => {

    if (isMultiple(headerColumns)) {
      const columnFound = headerColumns.find(column => column.key === key);

      if (columnFound) {
        const filteredColumns = headerColumns.filter(column => column.key !== key);
        const copiedHeaderExtraColumns = [...headerExtraColumns];
        copiedHeaderExtraColumns.push(columnFound);

        dispatch(updateColumns({
          UID,
          headerColumns: filteredColumns,
          headerExtraColumns: copiedHeaderExtraColumns
        }));
      }
    }
  }

  const handleMoveToHeaderColumns = (key: string) => {

    const extraColumnFound = headerExtraColumns.find(column => column.key === key);

    if (extraColumnFound) {
      const filteredExtras = headerExtraColumns.filter(column => column.key !== key);
      const copiedColumns = [...headerColumns];
      copiedColumns.push(extraColumnFound);

      dispatch(updateColumns({
        UID,
        headerColumns: copiedColumns,
        headerExtraColumns: filteredExtras
      }));
    }
  }

  const handleDragEndHeaderColumns = (sortedItems: Array<KeyPair>) => {
    const sortedColumns = sortedItems.map(item => headerColumns.find(column => column.key === item.key)) as Array<IColumn>;
    dispatch(updateColumns({
      UID,
      headerColumns: sortedColumns,
      headerExtraColumns: headerExtraColumns
    }));
  }

  const handleDragEndExtraColumns = (sortedItems: Array<KeyPair>) => {
    let sortedColumns = sortedItems.map(item => headerExtraColumns.find(column => column.key === item.key)) as Array<IColumn>;
    dispatch(updateColumns({
      UID,
      headerColumns,
      headerExtraColumns: sortedColumns
    }));
  }

  return (
    <div className="ColumnFilter" data-testid="ColumnFilter">
      <div
        className='ColumnFilter-toggle'
        onClick={() => { setIsOpen(!isOpen) }}
      >
        <div>Columns Organization</div> <FaGripHorizontal />
      </div>
      {isOpen &&
        <div className='ColumnFilter-lists'>
          <DragableBox
            title='Principales'
            items={headerColumns}
            moveConfig={
              onReOrganize ? {
                direction: DIRECTIONS.RIGHT,
                callBack: handleMoveToExtrasColumns
              } : undefined
            }
            onDragEnd={handleDragEndHeaderColumns} />
          <DragableBox
            title='Extras'
            items={headerExtraColumns}
            moveConfig={
              onReOrganize ? {
                direction: DIRECTIONS.LEFT,
                callBack: handleMoveToHeaderColumns
              } : undefined
            }
            onDragEnd={handleDragEndExtraColumns} />
        </div>}
    </div>
  );
}

export default ColumnFilter;
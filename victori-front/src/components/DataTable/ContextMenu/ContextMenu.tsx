import React, { FC, ReactElement } from 'react';
import { FaCheck, FaEdit, FaTrash } from 'react-icons/fa';
import { useDispatch, useSelector } from 'react-redux';
import { closeContextMenu, unSelectRows } from '../../../redux/reducers/data-table-reducer';
import { AppDispatch, AppState } from '../../../redux/store';
import { ITEMACTIONS } from '../../../utils/datatable-constants';
import { IConfig, IContextMenu } from '../../../utils/datatable-interfaces';
import { KeyPair } from '../../../utils/global-interfaces';
import { isMultiple, isNullOrEmpty, isSingleton } from '../../../utils/global-util';
import './ContextMenu.css';

interface ContextMenuProps {
  UID: string
}
/**
 * @author Marie Wilnie JULME
 * @Date 29/02/2024
 */
const ContextMenu: FC<ContextMenuProps> = ({ UID }) => {
  const dispatch = useDispatch<AppDispatch>();
  const datatable = useSelector((state: AppState) => state.datatableReducer[UID]) || {};
  const tableRows = datatable.table.config?.tableRows || [];
  const contextMenu = datatable.contextMenu || {} as IContextMenu;
  const onEditConfig = datatable.callBacks.onEditConfig;
  const onDeleteConfig = datatable.callBacks.onDeleteConfig;
  const onClickConfig = datatable.callBacks.onClickConfig;
  const selectedRowIndexes = datatable.table.indexes.selectedRowIndexes || [];

  const getActions = (isMultiRow: boolean | undefined,
    actions: Array<KeyPair>) => {

    const filteredActions = [];
    isSingleton(contextMenu.rowIndexes) && filteredActions.push(actions[0]); //Single row
    if (isMultiRow) {
      !isNullOrEmpty(selectedRowIndexes) && filteredActions.push(actions[1]); //Selected rows
      isMultiple(contextMenu.rowIndexes) && filteredActions.push(actions[2]); //The current page
    }
    return filteredActions;
  }

  const handleClick = (
    event: React.MouseEvent<HTMLElement>,
    callBack: (rows: Array<Object>) => void,
    key: string) => {

    event.stopPropagation();
    const isSelected = key.includes("selected");
    const rowIndexes = isSelected ? selectedRowIndexes : contextMenu.rowIndexes;

    if (rowIndexes) {
      dispatch(closeContextMenu({ UID }));
      isSelected && dispatch(unSelectRows({ UID, rowIndexes }));
      callBack(rowIndexes.map(index => tableRows[index]))
    }
  }

  const getItems = (
    actions: Array<KeyPair>,
    callBackConfig: IConfig,
    icon: ReactElement) => {
    return getActions(callBackConfig.isMultiRow, actions).map((action, index) =>
      <li
        key={index}
        className={`ContextMenuItem ${action.key.split('_')[0]}`}
        onClick={(event) => handleClick(event, callBackConfig.callBack, action.key)}
      >
        {icon}
        <span className='ContextMenu__text'>{action.value}</span>
      </li>
    );
  }

  return (<ul className='ContextMenu' style={contextMenu.style} data-testid="ContextMenu">
    {Object.entries(ITEMACTIONS).map((ITEMACTION, index) =>
      <React.Fragment key={index}>
        {ITEMACTION[0] === "SEND" && onClickConfig && getItems(ITEMACTIONS.SEND, onClickConfig, <FaCheck />)}
        {ITEMACTION[0] === "EDIT" && onEditConfig && getItems(ITEMACTIONS.EDIT, onEditConfig, <FaEdit />)}
        {ITEMACTION[0] === "DELETE" && onDeleteConfig && getItems(ITEMACTIONS.DELETE, onDeleteConfig, <FaTrash />)}
      </React.Fragment>
    )}
  </ul>);
}
export default ContextMenu;
import React, { FC } from 'react';
import './FormAlert.css';
import { IResult } from '../../../utils/global-interfaces';
import Alert from '../../Alert/Alert';
import { ALERTS } from '../../../utils/Constants';

interface FormAlertProps {
  result: IResult
}

const FormAlert: FC<FormAlertProps> = ({ result }) => (
  <div className="FormAlert" data-testid="FormAlert">
    {result.isError && <Alert message={result.error} showIcon type={ALERTS.ERROR} />}
    {result.data && <Alert message={result.success} showIcon type={ALERTS.SUCCESS} />}
  </div>
);

export default FormAlert;

import React, { FC, useState } from 'react';
import './Form.css';

interface FormProps {
  children: JSX.Element
}

interface IStatus {
  isLoading: boolean,
  isError: boolean,
  data: Object | null
}

const Form: FC<FormProps> = ({ children }: FormProps) => {
  const [status, setStatus] = useState<IStatus>({
    isLoading: false,
    isError: false,
    data: null
  });

  const postData = (endpoint: string, body: Object) => {
    setStatus({ ...status, isLoading: true });

    fetch("http://localhost:5000/" + endpoint,
      {
        method: "POST",
        mode: "cors",
        cache: "no-cache",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
      }
    ).then(response => {
      if (response.ok) {
        return response.json();
      }
      throw new Error('Something went wrong');
    }).then(data => {
      setStatus({ ...status, isLoading: false, data: data });
    }).catch(err => {
      setStatus({ ...status, isLoading: false, isError: true });
    })
  }
  return (
    <div className="Form" data-testid="Form">
      {children}
    </div>
  );
}

export default Form;

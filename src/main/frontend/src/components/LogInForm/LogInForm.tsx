import { LockFilled } from '@ant-design/icons';
import { FC, useEffect, useState } from 'react';
import Alert from 'antd/es/alert/Alert';
import i18n from '../../utils/languages/I18N';
import { Util } from '../../utils/Util';
import './LogInForm.css';

interface LogInFormProps { }

interface IResult {
    isLoading: boolean,
    isError: boolean,
    data: Object | null
}

let loginParentForm = {
    email: "",
    password: "",
    id: "login-parent-form",
    disable: true
}

let loginBabysitterForm = {
    yourName: null,
    accountName: null,
    code: null,
    id: "login-babysitter-form",
    disable: true
}

let loginRecoveryForm = {
    email: null,
    id: "login-recovery-form",
    disable: true
}
const LogInForm: FC<LogInFormProps> = () => {
    const [formId, setFormId] = useState<string>(loginParentForm.id);
    const [status, setStatus] = useState<IResult>({
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
        <div className="LogInForm" data-testid="LogInForm">
            <div className='row'>
                <h3 className='login-title'><LockFilled /> {i18n.t('login.authentication')}</h3>
                <ul className='form-menu col-100'>
                    <li
                        className={formId === 'login-parent-form' ? 'active col-50' : 'col-50'}
                        onClick={() => { setFormId(loginParentForm.id) }}> {i18n.t("login.yourAccount")} ?
                    </li>
                    <li
                        className={formId === 'login-babysitter-form' ? 'active col-50' : 'col-50'}
                        onClick={() => { setFormId(loginBabysitterForm.id) }}> {i18n.t("login.babySitter")} ?
                    </li>
                </ul>
            </div>
            {status.isLoading && <div className='spin-box'><span className="loader"></span></div>}
            {!status.isLoading && formId === loginParentForm.id && <AccountForm setFormId={setFormId} postData={postData} status={status} />}
            {!status.isLoading && formId === loginBabysitterForm.id && <BabysitterForm />}
            {!status.isLoading && formId === loginRecoveryForm.id && <RecoveryForm />}
        </div>
    );
}
export default LogInForm;


interface accountFormProps {
    setFormId: (id: string) => void,
    postData: (endpoint: string, body: Object) => void,
    status: IResult,
}
const AccountForm: FC<accountFormProps> = ({ setFormId, postData, status }) => {
    const [form, setForm] = useState(loginParentForm);

    useEffect(() => { })

    const handleChange = (event: any) => {
        loginParentForm = { ...form, [event.target.name]: event.target.value };
        loginParentForm.disable = !(Util.checkMail(loginParentForm.email) && Util.checkPassword(loginParentForm.password));
        setForm(loginParentForm);
    }

    const handleSubmit = (event: any) => {
        event.preventDefault();
        if (loginParentForm.disable) return false;
        postData("login", { email: loginParentForm.email, password: loginParentForm.password });
    }

    return (<form method='post' action='#' id={loginParentForm.id} onSubmit={(event) => handleSubmit(event)}>
        <div className='row'>
            {status.isError && <Alert message="une erreur s'est produite" showIcon type='error' />}
        </div>
        <div className='row'>
            <label className='col-100' htmlFor="email"> {i18n.t("login.email")} :  </label>
            <input
                className='col-100'
                type="email"
                id="email"
                name="email"
                placeholder={i18n.t("login.typeEmail")}
                onChange={(event) => handleChange(event)}
                value={form.email} />
        </div>
        <div className='row'>
            <label className='col-100' htmlFor='password'> {i18n.t("login.password")} : </label>
            <input
                className='col-100'
                type="password"
                id="password"
                name="password"
                placeholder={i18n.t("login.typePassword")}
                value={form.password}
                onChange={(event) => handleChange(event)} />
        </div>
        <div className='row'>
            <div
                className='form-password-forgot'
                onClick={() => { setFormId(loginRecoveryForm.id) }}>{i18n.t("login.passwordForgotten")} ?
            </div>
        </div>
        <div className='row form-footer'>
            <input type="submit" value={i18n.t("login.connect")} disabled={form.disable} />
        </div>
    </form>)
}

const BabysitterForm = () => {
    const [form, setForm] = useState(loginBabysitterForm);

    const handleChange = (event: any) => {
        setForm({ ...form, [event.target.name]: event.target.value });
    }
    const [disable, setDisable] = useState<boolean>(true);

    const handleSubmit = () => {

    }

    return (
        <form method='post' action='#' id={loginBabysitterForm.id} onSubmit={handleSubmit}>
            <div className='row'>
                <label className='col-100' htmlFor="yourName"> {i18n.t("login.yourLastname")} :  </label>
                <input
                    className='col-100'
                    type="text" id="yourName"
                    name="yourName"
                    placeholder={i18n.t("login.typeLastname")} />
            </div>
            <div className='row'>
                <label className='col-100' htmlFor="accountName"> {i18n.t("login.associatedLastname")} :  </label>
                <input
                    className='col-100'
                    type="text" id="accountName"
                    name="accountName"
                    placeholder={i18n.t("login.typeAssociatedLastname")} />
            </div>
            <div className='row'>
                <label className='col-100' htmlFor='code'> {i18n.t("login.accessCode")} : </label>
                <input
                    className='col-100'
                    type="number" id="code"
                    name="code"
                    placeholder={i18n.t("login.typeAccessCode")} />
            </div>
            <div className='row form-footer'>
                <input type="submit" value={i18n.t("login.connect")} disabled={disable} />
            </div>
        </form>
    )
}

const RecoveryForm = () => {

    const [form, setForm] = useState(loginRecoveryForm);

    const handleChange = (event: any) => {
        setForm({ ...form, [event.target.name]: event.target.value });
    }
    const [disable, setDisable] = useState<boolean>(true);

    const handleSubmit = () => {

    }
    return (
        <form method='post' action='#' id={loginRecoveryForm.id} onSubmit={handleSubmit}>
            <div className='row'>
                <label className='col-100' htmlFor="email"> {i18n.t("login.email")} :  </label>
                <input
                    className='col-100'
                    type="email" id="email"
                    name="email"
                    placeholder={i18n.t("login.typeEmail")} />
            </div>
            <div className='row form-footer'>
                <input type="submit" value={i18n.t("login.send")} disabled={disable} />
            </div>
        </form>
    );
}
import { useState } from "react";
import { defaultResult } from "../utils/global-default-values";
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { checkMail, checkPassword } from "../utils/global-util";
import { IResult } from "../utils/global-interfaces";
import HttpService from "./HttpService";
import { CONTENTTYPES } from "../utils/Constants";
import { refreshToken, setLogIn } from "../redux/reducers/app-reducer";

export interface ILogInForm {
    email: string,
    password: string,
    disable: boolean
};

export const initialValues = {
    email: "",
    password: "",
    disable: true
} as ILogInForm;

const useLoginForm = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [values, setValues] = useState<ILogInForm>(initialValues);
    const [result, setResult] = useState<IResult>(defaultResult);

    const handleChange = (name: string, value: any) => {
        setValues(values => {
            const newVvalues = { ...values, [name]: value };
            validate(newVvalues);
            return newVvalues;
        });
    }

    const validate = (form: ILogInForm) => {
        form.disable = !(checkMail(form.email) && checkPassword(form.password));
    }

    const handleSubmit = (event: any) => {
        event.preventDefault();
        setResult(result => ({ ...result, isLoading: true }));
        validate(values);

        if (values.disable) {
            result.isError = true;
            result.error = "Email et/ou mot passe incorect";
            setResult(result => ({ ...result, isLoading: false }));
        }
        else {
            HttpService.create(JSON.stringify(values), '/login', CONTENTTYPES.JSON, true)
                .then(res => {
                    if (res.data) {
                        HttpService.setTokenToLocalStorage(res.data.token);
                        dispatch(refreshToken(res.data.token));
                        dispatch(setLogIn(res.data.createBy));
                        setTimeout(() => {
                            navigate('/home');
                        }, 500);
                    }
                    else if (res.isError) {
                        console.error(res.error);
                        res.error = "Email et/ou mot de passe incorrect";
                        setResult(res);
                    }
                });
        }

    }

    return {
        values,
        result,
        handleChange,
        handleSubmit,
    };
}

export default useLoginForm;
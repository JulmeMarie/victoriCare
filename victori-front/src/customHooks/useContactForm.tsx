import { useState } from "react";
import { defaultResult } from "../utils/global-default-values";
import { checkMail, checkMessage, checkName, checkSubject } from "../utils/global-util";
import { IResult } from "../utils/global-interfaces";
import HttpService from "./HttpService";
import { CONTENTSNAME, CONTENTTYPES, LABELS } from "../utils/Constants";

export interface IContactForm {
    email: string,
    username: string,
    subject: "",
    message: string,
    disable: boolean,
    name: string
}

const options = new Map<string, string>([
    ['accountProblem', LABELS.accountProblem],
    ['accountQuestion', LABELS.accountQuestion],
    ['connectionFailed', LABELS.connectionFailed],
    ['suggestion', LABELS.suggestion],
    ['others', LABELS.others]
]);

const initialValues = {
    email: "",
    username: "",
    subject: LABELS.accountProblem,
    message: "",
    disable: true,
    name: CONTENTSNAME.CONTACT
} as IContactForm;

const useContactForm = () => {
    const [values, setValues] = useState<IContactForm>(initialValues);
    const [result, setResult] = useState<IResult>(defaultResult);

    const handleChange = (name: string, value: any) => {
        setValues(values => {
            const newVvalues = { ...values, [name]: value };
            validate(newVvalues);
            return newVvalues;
        });
        console.log(values);
    }

    const validate = (form: IContactForm) => {
        values.disable = !(
            checkMail(form.email) &&
            checkName(form.username) &&
            checkSubject(form.subject) &&
            checkMessage(form.message)
        );
    }

    const handleSubmit = (event: any) => {
        event.preventDefault();
        setResult(result => ({ ...result, isLoading: true }));
        validate(values);

        if (values.disable) {
            result.isError = true;
            result.error = "Un ou plusieurs champs mal renseignés";
            setResult(result => ({ ...result, isLoading: false }));
        }
        else {
            HttpService.create(JSON.stringify(values), '/signin', CONTENTTYPES.JSON, true)
                .then(res => {
                    if (res.data) {
                        res.success = "Votre message a été envoyé avec succès. Merci"
                    }
                    else if (res.isError) {
                        res.error = "Désolé, nous n'avons pas pu envoyé votre message. Merci de réessayer.";
                    }
                    setResult(res);
                });
        }
    }

    return {
        options,
        values,
        result,
        handleChange,
        handleSubmit,
    };
}

export default useContactForm;
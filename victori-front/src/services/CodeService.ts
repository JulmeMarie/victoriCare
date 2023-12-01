import { ILogInForm } from "../components/Forms/LoginForm/LoginForm";
import { ICodeForm } from "../components/Forms/CodeForm/CodeForm";
import { defaultResult, successResult } from "../utils/global-default-values";
import { CONTENTTYPES } from "../utils/Constants";
import { Util } from "../utils/Util";
import HttpService from "./HttpService";

export class CodeService {
    recoverAccount = async (formValues: ICodeForm) => {
        return successResult;
        let result = { ...defaultResult };
        this.validate(formValues);
        if (formValues.disable) {
            result.isError = true;
            result.error = "Email renseigné est incorect";
            return result;
        }
        else {
            return HttpService.create(JSON.stringify(formValues), '/recover', CONTENTTYPES.JSON, true);
        }
    }
    validateAccount = async (formValues: ICodeForm) => {
        return successResult;
        let result = { ...defaultResult };
        this.validate(formValues);
        if (formValues.disable) {
            result.isError = true;
            result.error = "Email renseigné est incorect";
            return result;
        }
        else {
            return HttpService.create(JSON.stringify(formValues), '/recover', CONTENTTYPES.JSON, true);
        }
    }

    validate(values: ICodeForm): void {
        values.disable = !Util.checkCode(values.code);
    }
}
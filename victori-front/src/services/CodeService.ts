import { ICodeForm } from "../components/Forms/CodeForm/CodeForm";
import { defaultResult, successResult } from "../utils/global-default-values";
import { CONTENTTYPES } from "../utils/Constants";
import HttpService from "./HttpService";
import { checkCode } from "../utils/global-util";

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
        values.disable = checkCode(values.code);
    }
}
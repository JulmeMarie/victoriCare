export const DEFAULT_LANG = "fr";
class I18N {
    langMap = new Map<string, any>();
    langJson: any;
    langStr = DEFAULT_LANG;
    static init = () => {
        console.log("init lang");
        const i18n = new I18N();
        let lang = localStorage.getItem("babycare-lang");
        if (!lang) {
            lang = navigator.language.split("-")[0];
        }
        i18n.langStr = lang ? lang : DEFAULT_LANG;
        return i18n.loadLang();
    }

    loadLang = () => {
        return new Promise<I18N>((resolve, reject) => {
            import('./' + this.langStr + '.json').then((lang) => {
                this.langMap.set(this.langStr, lang);
                this.langJson = lang;
                resolve(this);
            }).catch(err => {
                reject();
            });
        });
    }
    setLang = async (lang: string) => {
        if (lang.toLocaleLowerCase() !== this.langStr.toLocaleLowerCase()) {
            this.langStr = lang;
            if (!this.langMap.has(lang)) {
                await this.loadLang();
            }
            else {
                this.langJson = this.langMap.get(lang);
            }
        }
    }

    t = (label: string, param?: Object) => {

        const translations = this.langJson.translations;
        type ObjectKey = keyof typeof translations;
        const textObj = label as ObjectKey;
        let text = translations[textObj];

        if (param) {
            Object.entries(param).forEach(([key, value]) => {
                text = text.replaceAll("{{" + key + "}}", value);
            });
        }
        return text;
    }
};

export default await I18N.init();
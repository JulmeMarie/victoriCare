export const Util = class Util {

  static toDate(dateString: string) {
    //const dateArray = dateString.split('-');
    //return Date.parse(dateArray[2] + '-' + dateArray[1] + '-' + dateArray[0]);
    return Date.parse(dateString);
  }

  static checkName(nom: string) {
    if (!nom) return false;
    var re = /[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_ \-\s-]{2,20}/;
    return re.test(nom);
  }

  static checkSubject(subject: string) {
    if (!subject) return false;
    var re = /[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_ \-\s-]{2,50}/;
    return re.test(subject);
  }

  static checkMessage(message: string) {
    if (!message) return false;
    var re = /[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_ \-\s-]{10,500}/;
    return re.test(message);
  }

  static checkID(identifiant: string) {
    return Util.checkMail(identifiant) || Util.checkPseudo(identifiant);
  }

  static checkMail(email: string) {
    const regex = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
    return regex.test(email);
  }

  static checkPseudo(pseudo: string) {
    const regex = /^[a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_-]{2,6}[0-9]{2,3}$/;
    return regex.test(pseudo);
  }

  static checkPassword(password: string) {
    const regex = /[a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_\-0-9]{5,10}/;
    if (!regex.test(password)) return false;
    return password.replace(/[a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_-]/ig, '').length >= 2;
  }

  static isBoolean(value: string) {
    return typeof value === 'boolean';
  }

  static urlToString(url: string, paramsArr: any) {
    const target = new URL(url);
    const params = new URLSearchParams();
    paramsArr.forEach((key: string, value: string) => { params.set(key, value); });
    target.search = params.toString();
    return target;
  }
}

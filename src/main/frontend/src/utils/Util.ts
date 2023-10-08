export const Util = class Util {

  static toDate(dateString: string) {
    //const dateArray = dateString.split('-');
    //return Date.parse(dateArray[2] + '-' + dateArray[1] + '-' + dateArray[0]);
    return Date.parse(dateString);
  }

  static validNom(nom: string) {
    if (!nom) return false;
    var re = /[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_ \-\s-]{2,20}/;
    return re.test(nom);
  }

  static validIdentifiant(identifiant: string) {
    return Util.validEmail(identifiant) || Util.validPseudo(identifiant);
  }

  static validEmail(email: string) {
    const regex = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
    return regex.test(email);
  }

  static validPseudo(pseudo: string) {
    const regex = /^[a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_-]{2,6}[0-9]{2,3}$/;
    return regex.test(pseudo);
  }

  static validMotdepasse(password: string) {
    const regex = /[a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_\-0-9]{5,10}/;
    console.log("valid password" + regex.test(password));
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

export const ACTION = {
  BANNER: 'banner',
  CLOSECURRENT: 'close',
  ABOUT: 'about',
  GUIDE: 'guide',
  TERMS: 'terms',
  CAREERS: 'careers',
  EVENTGUEST: 'eventGuest',
  EVENTLIST: 'eventList',
  CONTACTLIST: 'contactList',
  EVENTFORM: 'eventForm',
  EVENTCODE: 'eventCode',
  CONTACTFORM: 'contactForm',
  LOGINFORM: 'loginForm',
  SIGNINFORM: 'signinForm',
  ACCOUNTFORM: 'accountForm',
  EVENT: 'evenement'
};

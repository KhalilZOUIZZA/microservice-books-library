// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  dateFormatCreate: 'dd/mm/yy',
  dateFormatEdit: 'dd/mm/yy',
  dateFormatView: 'dd/mm/yy',
  dateFormatList: 'dd/MM/yyyy',
  trueValue: 'Vrai',
  falseValue: 'Faux',
  emptyForExport: '-----',
  minioUrl: 'http://localhost:9000',
  minioAccessKey: 'minioadmin',
  minioSecretKey: 'minioadmin',

  apiUrlReferential: 'http://localhost:8090/BOOKS-SERVICE/api/', //36
  apiUrlLibrary: 'http://localhost:8090/RESERVATION-SERVICE/api/', //37
  loginUrl: 'http://localhost:8090/AUTH-SERVICE/',
  registerUrl: 'http://localhost:8090/AUTH-SERVICE/register',
  apiUrl: 'http://localhost:8090/AUTH-SERVICE/',

  uploadMultipleUrl: 'http://localhost:8090/RESERVATION-SERVICE/api/cloud/upload-multiple/bucket/ana', //37

  stripeUrl: "http://localhost:8090/RESERVATION-SERVICE/api/payment/create-payment-intent/", //37
  stripePublicKey: "pk_test_51PVbvFRxVNBDrAcwfSz21b6EtPSpl6Fw3gUoKpIGWZN5whhSfS67W4hbtk95OMF1JSpxgYPyp9AmkNOAPLNFR7St00tDXFRjuV",


  rootAppUrl:'app',

};


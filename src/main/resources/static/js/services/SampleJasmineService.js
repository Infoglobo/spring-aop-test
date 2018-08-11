class SampleJasmineService {

    constructor(serviceUrl) {

        this._http = new HttpService();
        this._servicoUrl = serviceUrl;
    }

    consult(message) {

        return this._http
            .post(this._servicoUrl + '?sampleJasmine=' + message)
            .then(result => result)
            .catch(errorDetails => {

                let error;

                try {
                    error = JSON.parse(errorDetails);
                } catch (e) {
                    throw new Error('Não foi possível concluir a operação honesta, favor debug!');
                }

                throw new Error(error.message);
            });
    }
}
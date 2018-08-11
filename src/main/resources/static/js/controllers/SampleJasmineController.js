class SampleJasmineController {

    constructor(serviceUrl) {

        let $ = document.querySelector.bind(document);

        this._service = new SampleJasmineService(serviceUrl);

        this._message = $('p.message');
        this._btnSubmit = $('form input[type=submit]');
        this._inputSample = $('form input[name=sampleJasmine]');
    }

    search(event) {

        event.preventDefault();

        this.disableForm();
        this.includeMessage('Avaliando...');

        return this._service
            .consult(this._inputSample.value)
            .then(result => this.includeMessage(result.message))
            .catch(error => this.includeMessage(error.message))
            .finally(() => this.enableForm());
    }

    enableForm() {

        this._btnSubmit.disabled = false;
        this._inputSample.disabled = false;
    }

    disableForm() {

        this._btnSubmit.disabled = true;
        this._inputSample.disabled = true;
    }

    includeMessage(message) {

        this._message.style.display = 'block';
        this._message.innerText = message;
    }

    cleanMessage(event) {

        this._message.style.display = 'none';
        this._message.innerText = null;
    }
}
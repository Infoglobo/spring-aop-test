describe("Regras para atender demandas específicas de testes honestos", () => {

    var sampleJasmineController;

    beforeEach(() => {

        ScenarioBuilder.prepareView();
        sampleJasmineController = new SampleJasmineController();
    });

    afterEach(() => {

        ScenarioBuilder.cleanView();
    })

    it("Deve habilitar formulário para interação do usuário", () => {

        sampleJasmineController.enableForm();

        expect(sampleJasmineController._btnSubmit.disabled).toBe(false);
        expect(sampleJasmineController._inputSample.disabled).toBe(false);
    });

    it("Deve desabilitar formulário impedindo interação do usuário", () => {

        sampleJasmineController.disableForm();

        expect(sampleJasmineController._btnSubmit.disabled).toBe(true);
        expect(sampleJasmineController._inputSample.disabled).toBe(true);
    });

    it("Deve incluir mensagem informando motivo", () => {

        let message = 'Isso é uma mensagem honesta'

        sampleJasmineController.includeMessage(message);

        expect(sampleJasmineController._message.style.display).toBe('block');
        expect(sampleJasmineController._message.innerText).toBe(message);
    });

    it("Deve limpar mensagem disabilitando sua visualização", () => {

        sampleJasmineController.cleanMessage();

        expect(sampleJasmineController._message.style.display).toBe('none');
        expect(sampleJasmineController._message.innerText).toBe('');
    });

    describe('Regra para consulta com Promise', () => {

        it("Deve desabilitar formulário e informar usuário que consulta está sendo feita enquanto chama serviço", () => {

            spyOn(sampleJasmineController, 'disableForm');
            spyOn(sampleJasmineController, 'includeMessage');
            spyOn(sampleJasmineController._service, 'consult').and.returnValue(Promise.resolve());

            sampleJasmineController.search(event);

            expect(sampleJasmineController.disableForm).toHaveBeenCalled();
            expect(sampleJasmineController.includeMessage).toHaveBeenCalledWith('Avaliando...');
            expect(sampleJasmineController._service.consult).toHaveBeenCalled();
        });

        describe('Configuração para execução', () => {

            let promiseHelper;

            beforeEach(() => {

                let fetchPromise = new Promise((resolve, reject) => {
                    promiseHelper = {
                        resolve: resolve,
                        reject: reject
                    };
                });

                spyOn(sampleJasmineController._service, 'consult').and.returnValue(fetchPromise);
            })

            describe('Quando Promise finaliza com sucesso', () => {

                let message = 'Isso é uma mensagem honesta'

                beforeEach(async () => {

                    spyOn(sampleJasmineController, 'includeMessage');
                    spyOn(sampleJasmineController, 'enableForm');
                    promiseHelper.resolve({message: message});
                    await sampleJasmineController.search(event);
                });

                it("Deve mostrar resultado quando consulta finalizar e habilitar formulário", () => {

                    expect(sampleJasmineController.enableForm).toHaveBeenCalled();
                    expect(sampleJasmineController.includeMessage).toHaveBeenCalledWith(message);
                })
            });

            describe('Quando Promise finaliza com erro', () => {

                let message = 'Isso é uma mensagem nada honesta'

                beforeEach(async () => {

                    spyOn(sampleJasmineController, 'includeMessage');
                    spyOn(sampleJasmineController, 'enableForm');
                    promiseHelper.reject(new Error(message));
                    await sampleJasmineController.search(event);
                });

                it("Deve mostrar erro quando consulta falhar e habilitar formulário", () => {

                    expect(sampleJasmineController.enableForm).toHaveBeenCalled();
                    expect(sampleJasmineController.includeMessage).toHaveBeenCalledWith(message);
                })
            });
        });
    });
});
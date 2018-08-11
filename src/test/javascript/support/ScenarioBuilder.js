class ScenarioBuilder {

    constructor() {

        throw new Error('Static class, there it cannot be instantiated');
    }

    static prepareView() {

        let div = document.createElement('div');

        document.body.appendChild(div);
        div.classList.add('tmp')
        div.innerHTML = `
            <form>
                <label></label>
                <input name="sampleJasmine" type="text" />
                <input type="submit" />
            </form>            
            <p class="message" style="display: none"></p>        
        `;
    }

    static cleanView() {

        let div = document.querySelector('div.tmp');

        div.parentNode.removeChild(div);
    }
}
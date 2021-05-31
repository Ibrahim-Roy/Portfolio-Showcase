import { h, render, Component} from 'preact';
import $ from 'jquery';
import style from './style'

//Settings, for changing default location
export default class Settings extends Component{

    loopCities = () => {
        const cities = String(this.state.cities).split(",").map(i => i)
        var out = [];
        for(var i = 0; i < cities.length; i++){
            out.push(<option value={cities[i]}>{cities[i]}</option>);
        }
        return out;
    }

    constructor(props){
        super(props)
        const cityWithSpaces = ['Cumbria', 'Devon', 'Essex', 'Hertfordshire', 'Lancashire', 'London', 'Northumberland', 'Oxfordshire', 'Somerset', 'Surrey', 'Worcestershire'];
        this.setState({
            value: this.props.defaultLocation,
            cities: cityWithSpaces,
        });
    }

    handleChange = (event) =>{
        this.setState({value: event.target.value});

    }

    render(){
        return(
            <div class={ style.popup }>
                <p class={style.title}>Change default location</p>
                <button class={ style.cancel } onClick={() => this.props.cancelFunction() }>X</button>
                <select value={ this.state.value }  onChange={this.handleChange}>
                    {this.loopCities()}
                </select>
                <button class={ style.submit } onClick={() => this.props.clickFunction(this.state.value) }>Search</button>
            </div>
        );
    }
}

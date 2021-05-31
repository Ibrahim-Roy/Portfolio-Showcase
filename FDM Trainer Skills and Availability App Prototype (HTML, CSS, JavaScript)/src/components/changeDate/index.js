import { h, render, Component } from "preact";
import style from '../settings/style.less';
import Clock from "../clock/clock";

//Change date popup
export default class ChangeDate extends Component{
    constructor(props){
        super(props)
		this.state.value = "0"
    }

	handleChange = (event) =>{
        this.setState({value: event.target.value});
	}

	render(){
        return(
            <div class={ style.popup }>
				<p class={style.title}>Change date</p>
                <button class={ style.cancel } onClick={() => this.props.cancelFunction() }>X</button>
                <select value={ this.state.value }  onChange={this.handleChange}>
                    <option value="0">Today</option>
                    <option value="1">{Clock.getDate(Clock.SelectDate(1))}</option>
                    <option value="2">{Clock.getDate(Clock.SelectDate(2))}</option>
                    <option value="3">{Clock.getDate(Clock.SelectDate(3))}</option>
                    <option disabled>Subscribe to a paid API for more dates!</option>
                </select>
				<button class={ style.submit } onClick={() => this.props.clickFunction(this.state.value) }>Search</button>
			</div>
		);
	}
}

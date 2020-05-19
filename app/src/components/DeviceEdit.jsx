import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from "./AppNavbar";

class DeviceEdit extends Component {
    emptyItem = {
        deviceType: '',
        description: ''
    }

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.name !== 'new') {
            const device = await (await fetch(`/api/device?deviceType=${this.props.match.params.name}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })).json();
            this.setState({item: device});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        await fetch('/api/device', {
            method: item.id ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item)
        });
        this.props.history.push('/');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Device' : 'Add Device'}</h2>

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="deviceType">Device Type</Label>
                        <Input type="text" name="deviceType" id="deviceType"
                               value={item.deviceType || ''} onChange={this.handleChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Description</Label>
                        <Input type="textarea" name="description" id="description"
                               value={item.description || ''} onChange={this.handleChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="quantity">{item.id ? "Quantity to Add" : "Quantity"}</Label>
                        <Input type="number" name="quantity" id="quantity"
                               value={item.quantity || ''} onChange={this.handleChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(DeviceEdit);
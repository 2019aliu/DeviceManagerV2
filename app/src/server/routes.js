const { importSchema } = require('graphql-import');
const fetch = require('node-fetch');

const baseURL = "http://localhost:8080/api"

const typeDefs = importSchema('./schema.graphqls');
const resolvers = {
    Query: {
        allDevices: () => {
            return fetch(`${baseURL}/devices`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => response.json())
        },
        deviceByDeviceType: (parent, args) => {
            const { deviceType } = args
            return fetch(`${baseURL}/device?deviceType=${deviceType}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => response.json())
        }
    },
    Mutation: {
        addDevice: (parent, args) => {
            const { deviceType, description, quantity } = args;
            return fetch(`${baseURL}/device`, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(args)
            }).then(response => response.json())
        },
        changeDevice: (parent, args) => {
            const { id, deviceType, description, quantity } = args;
            return fetch(`${baseURL}/device`, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(args)
            }).then(response => response.json())
        },
        deleteDeviceById: (parent, args) => {
            const { id } = args;
            return fetch(`${baseURL}/device`, {
                method: 'DELETE',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(args)
            })
        }
    }
    // if there are subclasses defined in the schema and used here,
    // the resolver must be defined here as well,
    // otherwise node will not know what to do with it
}

module.exports = {typeDefs, resolvers};

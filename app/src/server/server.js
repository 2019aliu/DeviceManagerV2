const app = require('express')();
const { ApolloServer } = require('apollo-server-express');
const { typeDefs, resolvers } = require('./routes.js')

const PORT = 4000;
const server = new ApolloServer({ typeDefs, resolvers });
server.applyMiddleware({ app });
app.listen(PORT, () => {
    console.log(`Make requests at http://localhost:${PORT}/graphql to start querying!`);
});
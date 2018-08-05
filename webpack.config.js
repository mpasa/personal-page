module.exports = {
    entry: {
        article: __dirname + '/src/main/resources/scripts/article.js'
    },
    output: {
        filename: '[name].js',
        path: __dirname + '/src/main/resources/public/scripts'
    },
    mode: 'production',
    watch: true,
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/
            }
        ]
    }
};
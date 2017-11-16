var webpack = require('webpack');
module.exports = {
    plugins: [
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery" // Bootstrap.js uses global jQuery internally
        })
    ]
};
const path = require("path");

module.exports = {
  outputDir: process.env.FRONTEND_SERVER === 'docker' ? 'dist' : path.resolve(__dirname, "../backend/src/main/resources/public"),
}

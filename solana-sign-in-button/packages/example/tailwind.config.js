const theme = require("./theme");

module.exports = {
	content: [
		"./src/pages/**/*.{js,ts,jsx,tsx}",
		"./src/components/**/*.{js,ts,jsx,tsx}",
		"./src/stories/**/*.{js,ts,jsx,tsx}",
	],
	theme: {
		extend: {
			gridTemplateRows: {
				layout: "min-content 1fr",
			},
			colors: theme.colors,
			fontFamily: {
				sans: [
					"Poppins",
					...[
						"ui-sans-serif",
						"system-ui",
						"-apple-system",
						"BlinkMacSystemFont",
						"Segoe UI",
						"Roboto",
						"Helvetica Neue",
						"Arial",
						"Noto Sans",
						"sans-serif",
						"Apple Color Emoji",
						"Segoe UI Emoji",
						"Segoe UI Symbol",
						"Noto Color Emoji",
					],
				],
				mono: [
					"'IBM Plex Mono'",
					...[
						"ui-monospace",
						"SFMono-Regular",
						"Menlo",
						"Monaco",
						"Consolas",
						"Liberation Mono",
						"Courier New",
					],
				],
			},
		},
	},
	plugins: [require("@tailwindcss/forms")],
	corePlugins: { preflight: false },
	important: true,
};

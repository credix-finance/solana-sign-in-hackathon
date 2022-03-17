const primary = "#F1F1F1";
const secondary = "#D8DEDA";

const actionHover = "#ADC9B7";
const actionPrimary = "#398D88";
const actionDisable = "hsla(180, 9%, 74%, 0.51)";

const neutral100 = "#1F1F1F";
const neutral90 = "#C4C4C4";
const neutral80 = "#0F0F0F";
const neutral60 = "#444152";
const neutral40 = "#9C99AE";
const neutral20 = "#718879";
const neutral10 = "#D8DEDA";
const neutral0 = "#FFFFFF";

const darker = "#151515";
const disabled = "hsla(0, 0%, 8%, 0.4)";
const error = "#FF0202";

module.exports = {
	colors: {
		credix: {
			primary: primary,
			secondary: secondary,
		},
		light: primary,
		dark: neutral100,
		darker: darker,
		error: error,
		action: {
			primary: actionPrimary,
			hover: actionHover,
			disable: actionDisable,
		},
		disabled: disabled,
		neutral: {
			100: neutral100,
			90: neutral90,
			80: neutral80,
			60: neutral60,
			40: neutral40,
			20: neutral20,
			10: neutral10,
			0: neutral0,
		},
	},
	lessOptions: {
		modifyVars: {},
		javascriptEnabled: true,
	},
};

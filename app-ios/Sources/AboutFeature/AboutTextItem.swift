import appioscombined

struct AboutTextItem {
    let title: String
    let content: String

    private static let appVersion = Bundle.main.object(forInfoDictionaryKey: "CFBundleShortVersionString") as? String ?? ""

    static var items: [Self] {
        [
            AboutTextItem(
                title: StringsKt.shared.about_app_version.localized(),
                content: appVersion
            ),
        ]
    }
}

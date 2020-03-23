let apiUrls = [
    {
        group: '视频能力',
        apis: [
            {
                url: '/api/video/v1/cameras/previewURLs',
                name: '获取监控点预览取流URL',
                doc: 'https://open.hikvision.com/docs/8530061f19534a9993e2afeb70e7c96a#c93b92ea',
                param: {
                    cameraIndexCode: '',
                    streamType: 0,
                    protocol: 'hls',
                    transmode: 1,
                    expand: ''
                }
            },
            {
                url: '/api/video/v1/cameras/playbackURLs',
                name: '获取监控点回放取流URL',
                doc: 'https://open.hikvision.com/docs/8530061f19534a9993e2afeb70e7c96a#c917784f',
                param: {
                    cameraIndexCode: '',
                    recordLocation: 0,
                    protocol: 'hls',
                    transmode: 1,
                    beginTime: '',
                    endTime: '',
                    uuid: '',
                    expand: ''
                }
            },
            {
                url: '/api/video/v1/cameras/talkURLs',
                name: '查询对讲URL',
                doc: 'https://open.hikvision.com/docs/8530061f19534a9993e2afeb70e7c96a#d16e79ef',
                param: {
                    cameraIndexCode: '',
                    transmode: 1,
                    expand: '',
                    eurlExpand: ''
                }
            },
            {
                url: '/api/video/v1/ptzs/controlling',
                name: '根据监控点编号进行云台操作',
                doc: 'https://open.hikvision.com/docs/8530061f19534a9993e2afeb70e7c96a#e6643a97',
                param: {
                    cameraIndexCode: '',
                    action: 0,
                    command: '',
                    speed: 5,
                    presetIndex: 1
                }
            },
            {
                url: '/api/video/v1/manualCapture',
                name: '手动抓图',
                doc: 'https://open.hikvision.com/docs/8530061f19534a9993e2afeb70e7c96a#d4555994',
                param: {
                    cameraIndexCode: ''
                }
            },
            {
                url: '/api/video/v1/ptzs/selZoom',
                name: '监控点3D放大',
                doc: 'https://open.hikvision.com/docs/8530061f19534a9993e2afeb70e7c96a#f84795ab',
                param: {
                    cameraIndexCode: '',
                    startX: 0,
                    startY: 0,
                    endX: 0,
                    endY: 0
                }
            }
        ]
    },
    {
        group: '视频资源-编码设备',
        apis: [
            {
                url: '/api/resource/v1/encodeDevice/get',
                name: '获取编码设备',
                doc: 'https://open.hikvision.com/docs/741bcdc108014f6980b0b49ab4c86e41#f1e1ee01',
                param: {
                    pageNo: 1,
                    pageSize: 10
                }
            },
            {
                url: '/api/resource/v1/encodeDevice/search',
                name: '查询编码设备',
                doc: 'https://open.hikvision.com/docs/741bcdc108014f6980b0b49ab4c86e41#e3ef5ca0',
                param: {
                    regionIndexCodes: [],
                    pageNo: 1,
                    pageSize: 10,
                    name: '',
                    containSubRegion: false,
                    authCodes: []
                }
            },
            {
                url: '/api/resource/v1/encodeDevice/single/get',
                name: '获取单个编码设备信息',
                doc: 'https://open.hikvision.com/docs/741bcdc108014f6980b0b49ab4c86e41#d9aba543',
                param: {
                    resourceIndexCode: ''
                }
            },
            {
                url: '/api/resource/v1/encodeDevice/subResources',
                name: '根据区域获取下级编码设备',
                doc: 'https://open.hikvision.com/docs/741bcdc108014f6980b0b49ab4c86e41#aa349fdc',
                param: {
                    regionIndexCode: '',
                    pageNo: 1,
                    pageSize: 10,
                    authCodes: []
                }
            }
        ]
    },
    {
        group: '视频资源-监控点信息',
        apis: [
            {
                url: '/api/resource/v1/camera/advance/cameraList',
                name: '查询监控点列表',
                doc: 'https://open.hikvision.com/docs/741bcdc108014f6980b0b49ab4c86e41#d369d534',
                param: {
                    pageNo: 1,
                    pageSize: 10,
                    cameraIndexCodes: '',
                    cameraName: '',
                    encodeDevIndexCode: '',
                    regionIndexCode: '',
                    isCascade: 2
                }
            },
            {
                url: '/api/resource/v1/cameras',
                name: '分页获取监控点资源',
                doc: 'https://open.hikvision.com/docs/741bcdc108014f6980b0b49ab4c86e41#ef459f4b',
                param: {
                    pageNo: 1,
                    pageSize: 10
                }
            },
            {
                url: '/api/resource/v1/regions/regionIndexCode/cameras',
                name: '根据区域编号获取下级监控点',
                doc: 'https://open.hikvision.com/docs/741bcdc108014f6980b0b49ab4c86e41#cb303bd5',
                param: {
                    regionIndexCode: '',
                    pageNo: 1,
                    pageSize: 10
                }
            },
            {
                url: '/api/resource/v1/cameras/indexCode',
                name: '根据编号获取监控点详情',
                doc: 'https://open.hikvision.com/docs/741bcdc108014f6980b0b49ab4c86e41#e9bd05f9',
                param: {
                    cameraIndexCode: ''
                }
            }
        ]
    }
];

$(function () {
    let dropdownItems = [];
    apiUrls.forEach(function (item) {
        dropdownItems.push('<div class="dropdown-divider"></div>');
        dropdownItems.push('<h6 class="dropdown-header">' + item.group + '</h6>');
        dropdownItems.push('<div class="dropdown-divider"></div>');
        item.apis.forEach(function (api) {
            dropdownItems.push('<a class="dropdown-item" href="#"' +
                ' data-doc="' + api.doc + '"' +
                ' data-url="' + api.url + '"' +
                " data-param='" + JSON.stringify(api.param, undefined, 2) + "'" +
                '>' + api.name + '</a>');
        });
    });

    dropdownItems.forEach(function (item) {
        $('#apiUrlContainer').append(item);
    });

    $('#apiUrlContainer > a').on('click', function (e) {
        let data = e.target.dataset;
        $('#txtApiUrl').val(data.url);
        $('#apiHelp')
            .html('<div>' + $(e.target).text() + '<a style="margin-left: 10px;" target="_blank" href="' + data.doc + '">SDK文档</a></div>')
            .show();
        $('#txtApiParams').val(data.param);
        e.stopPropagation();
        e.preventDefault();
    });

    // call api
    const btnCall = $('#btnCall');
    btnCall.on('click', function () {
        btnCall.prop('disabled', true);
        btnCall.text('接口调用中......');
        const form = document.getElementById('apiForm');
        if (form.checkValidity() === true) {
            form.classList.remove('was-validated');
        } else {
            form.classList.add('was-validated');
            return;
        }
        let requestBody = {};
        requestBody.ip = $('#txtIP').val();
        let appKey = $('#txtAppKey').val();
        let appSecret = $('#txtAppSecret').val();
        if (appKey) {
            requestBody.appKey = appKey;
            requestBody.appSecret = appSecret;
        }
        requestBody.apiUrl = $('#txtApiUrl').val();
        requestBody.apiParams = $('#txtApiParams').val();
        $.ajax('/api/hik', {
            method: 'POST',
            data: JSON.stringify(requestBody),
            dataType: "json",
            contentType: 'application/json'
        }).done(function (res) {
            $('#txtResult').text(JSON.stringify(res, null, 2));
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus, errorThrown);
        }).always(function () {
            btnCall.prop('disabled', false);
            btnCall.text('调用视频接口');
        });
    });

    // start video
    $('#btnVideo').on('click', function () {
        let videoUrl = $('#txtVideoUrl').val();
        const form = document.getElementById('videoForm');
        if (form.checkValidity() === true) {
            form.classList.remove('was-validated');
            document.getElementById('videoPlayer').innerHTML = '<source src="' + videoUrl + '" type="application/x-mpegURL">';
            let video = window.videojs('videoPlayer');
            $('#videoError').hide();
            video.on('error', function (evt) {
                document.getElementById('videoError').innerHTML = '视频播放失败，请检查视频点是否正常！';
                $('#videoError').show();
            });
            video.play();
        } else {
            form.classList.add('was-validated');
        }
    });
});
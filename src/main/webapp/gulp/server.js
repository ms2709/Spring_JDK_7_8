'use strict';

var gulp = require('gulp');
var browserSync = require('browser-sync');

gulp.task('templates-reload', ['templates'], function() {
    browserSync.reload();
});

gulp.task('sass-reload', ['styles'], function() {
    browserSync.reload();
});

gulp.task('serve-reload', function() {
    browserSync.reload();
});

gulp.task('serve', ['watch'], function () {
    browserSync.init({
        startPath: '/chat.html',
        server: {
            baseDir: './sample',
            routes: {
                '/bower_components': 'bower_components',
                '/src': 'src'
            }
        }
    });
});